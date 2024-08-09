package com.techelevator.dao;

import com.techelevator.model.Rating;
import com.techelevator.model.Review;

public interface RatingDao {

//    Integer addRatingAndReviewAndReturnGameId(Rating rating, Review review);

    Rating addRating(Rating rating);

    Rating getRatingById(Rating rating);

}
