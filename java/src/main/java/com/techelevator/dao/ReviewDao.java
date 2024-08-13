package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewsByGameId(int game_id);

    List<Review> getProfileReviews(int user_id);

    List<Review> addReview(Review review, int userId);

    List<Review> updateAndEditReview(Review review, int user_id);

    int deleteMyReview(Review review, int user_id);

    List<Review> adminFetchReviewsForSpecificUser(Review review);

    int adminDeleteReview(Review review);

    List<Review> getReviewsByReviewId(int review_id);

}
