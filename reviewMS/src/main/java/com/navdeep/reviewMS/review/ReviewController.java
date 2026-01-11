package com.navdeep.reviewMS.review;

import com.navdeep.reviewMS.review.dto.ReviewMessage;
import com.navdeep.reviewMS.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService=reviewService;
        this.reviewMessageProducer=reviewMessageProducer;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewForSpecificCompany(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReviewForSpecificCompany(reviewId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @RequestBody Review review){
        Boolean isCreated=reviewService.createReview(companyId, review);
        if(isCreated){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review created Successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Review not Created", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        Boolean isDeleted=reviewService.deleteReview(reviewId);

        if(isDeleted) return new ResponseEntity<>("Review Deleted Successfully", HttpStatus.OK);
        else return new ResponseEntity<>("Review not Found", HttpStatus.NOT_FOUND);
    }
//
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview){
        Boolean isUpdated=reviewService.updateReview(reviewId, updatedReview);

        if(isUpdated) return new ResponseEntity<>(reviewService.getReviewForSpecificCompany(reviewId), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewsList=reviewService.getAllReviews(companyId);
        return reviewsList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
//        Integer totalReviews=reviewsList.size();
//        Double sumOfReviewsRating=0d;
//
//        if(totalReviews!=0){
//            for(Review review: reviewsList){
//                sumOfReviewsRating+=review.getRating();
//            }
//
//            return sumOfReviewsRating/totalReviews;
//        }
//
//        return 0;
    }
}
