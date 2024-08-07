package com.techelevator.dao;

import com.techelevator.model.Review;

public interface ReviewDao {

    Review getReviewsById(int review_id);

    Review addReview(Review review);

    Review updateAndEditReview(Review review);

    Review deleteReview(int review_id);
}
