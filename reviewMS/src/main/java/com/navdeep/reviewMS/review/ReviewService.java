package com.navdeep.reviewMS.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    Review getReviewForSpecificCompany(Long reviewId);
    Boolean createReview(Long companyId, Review review);
    Boolean deleteReview(Long reviewId);
    Boolean updateReview(Long id, Review review);
}
