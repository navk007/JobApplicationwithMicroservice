package com.navdeep.reviewMS.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService=reviewService;
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
}
