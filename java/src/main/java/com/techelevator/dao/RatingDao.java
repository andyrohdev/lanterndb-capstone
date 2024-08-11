package com.techelevator.dao;

import com.techelevator.model.CollectionList;
import com.techelevator.model.Rating;
import com.techelevator.model.Review;

import java.util.List;

public interface RatingDao {

//    Integer addRatingAndReviewAndReturnGameId(Rating rating, Review review);

    Rating addRating(Rating rating);

    Rating getRatingById(Rating rating);

    List<Rating> fetchRatingsByGameId(int game_id);

}
