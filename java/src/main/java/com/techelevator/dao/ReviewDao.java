package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewsByGameId(int game_id);

    List<Review> addReview(Review review, int userId);

    Review updateAndEditReview(Review review);

    Review deleteReview(int review_id);
}
