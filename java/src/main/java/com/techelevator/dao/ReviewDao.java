package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewsByGameId(int game_id, int user_id);

    List<Review> addReview(Review review, int user_id);

    Review updateAndEditReview(Review review);

    Review deleteReview(int review_id);
}
