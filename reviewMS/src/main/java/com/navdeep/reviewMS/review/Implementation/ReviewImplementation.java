package com.navdeep.reviewMS.review.Implementation;

import com.navdeep.reviewMS.review.Review;
import com.navdeep.reviewMS.review.ReviewRepository;
import com.navdeep.reviewMS.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;
    public ReviewImplementation(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public Review getReviewForSpecificCompany(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Boolean createReview(Long companyId, Review review) {
        if(companyId != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Boolean deleteReview(Long reviewId) {
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(review!=null){
            reviewRepository.deleteById(reviewId);
            return true;
        }

        return false;
    }

    @Override
    public Boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setDescription(updatedReview.getDescription());
            review.setTitle(updatedReview.getTitle());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            return true;
        }
        else return false;
    }
}
