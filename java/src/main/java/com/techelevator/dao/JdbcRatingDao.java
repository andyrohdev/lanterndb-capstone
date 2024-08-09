package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Rating;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class JdbcRatingDao implements RatingDao{

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final JdbcUserDao jdbcUserDao;

    private Review review = new Review();

    public JdbcRatingDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }
    @Override
    public Rating getRatingById(Rating rating) {
        Rating currentRating = null;

        String sql = "SELECT rating_id, rating_score, user_id, game_id, game_title FROM ratings WHERE rating_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, rating.getRating_id());
            if (results.next()) {
                currentRating = mapRowToRating(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return currentRating;

    }
    @Override
    public Rating addRating(Rating rating) {
        Rating addedRating = null;
        String sql = "INSERT INTO ratings (rating_score, user_id, game_id, game_title)\n" +
                "    VALUES (?, ?, ?, ?)\n" +
                "    RETURNING rating_id;";
        int newId = jdbcTemplate.queryForObject(sql, int.class, rating.getRating_score(), rating.getUser_id(), rating.getGame_id(), rating.getGame_title());
        rating.setRating_id(newId);
        addedRating = getRatingById(rating);
        return addedRating;
    }

    public List <Review> getReviewsByGameId(int game_id) {
        List<Review> reviewsList = new ArrayList<>();
        String sql = "SELECT review_id, game_id, user_id, review_title, review_text FROM reviews WHERE game_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, game_id);
            while(results.next()){
                reviewsList.add(mapRowToReview(results));
            }


        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return reviewsList;
    }

//    @Override
//    public Integer addRatingAndReviewAndReturnGameId(Rating rating, Review review) {
//
//        Integer retrievedGameId = null;
//        List<Review> reviewsByGameId = null;
//
//        // Insert the rating and retrieve the rating_id and game_id
//        String insertRatingSql = "INSERT INTO ratings (rating_score, user_id, game_id, game_title) " +
//                "VALUES (?, ?, ?, ?) RETURNING rating_id, game_id";
//        Map<String, Object> result = jdbcTemplate.queryForMap(insertRatingSql, rating.getRating_score(), rating.getUser_id(), rating.getGame_id(), rating.getGame_title());
//        Integer ratingId = (Integer) result.get("rating_id");
//        retrievedGameId = (Integer) result.get("game_id");
//
//        // Insert the review using the retrieved rating_id
//        String insertReviewSql = "INSERT INTO reviews (game_id, user_id, rating_id, review_title, review_text) " +
//                "VALUES (?, ?, ?, ?, ?)";
//        jdbcTemplate.update(insertReviewSql, retrievedGameId, review.getUser_id(), ratingId, review.getReview_title(), review.getReview_text());
//
//        reviewsByGameId = getReviewsByGameId(retrievedGameId);
//        // Return the game_id
//        return retrievedGameId;
//    }

//    @Override
//    public List<Review> addRatingAndReviewAndReturnGameId(Rating rating, Review review) {
//        List<Review> reviewsByGameId = new ArrayList<>();
//        Integer retrievedGameId = null;
//
//        // Insert the rating and retrieve the rating_id and game_id
//        String insertRatingSql = "INSERT INTO ratings (rating_score, user_id, game_id, game_title) " +
//                "VALUES (?, ?, ?, ?) RETURNING rating_id, game_id";
//        try {
//            Map<String, Object> result = jdbcTemplate.queryForMap(insertRatingSql,
//                    rating.getRating_score(), rating.getUser_id(), rating.getGame_id(), rating.getGame_title());
//            Integer ratingId = (Integer) result.get("rating_id");
//            retrievedGameId = (Integer) result.get("game_id");
//
//            // Insert the review using the retrieved rating_id
//            String insertReviewSql = "INSERT INTO reviews (game_id, user_id, rating_id, review_title, review_text) " +
//                    "VALUES (?, ?, ?, ?, ?)";
//            jdbcTemplate.update(insertReviewSql, retrievedGameId, review.getUser_id(), ratingId, review.getReview_title(), review.getReview_text());
//
//            // Retrieve all reviews for the game_id
//            reviewsByGameId = getReviewsByGameId(retrievedGameId);
//
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to DB!", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//
//        return reviewsByGameId;
//    }


    private RowMapper<Rating> ratingRowMapper() {
        return (rs, rowNum) -> {
            Rating rating = new Rating();
            rating.setRating_id(rs.getInt("rating_id"));
            rating.setRating_score(rs.getInt("rating_score"));
            rating.setUser_id(rs.getInt("user_id"));
            rating.setGame_id(rs.getInt("game_id"));
            rating.setGame_title(rs.getString("game_title"));
            return rating;
        };
    }


    private Review mapRowToReview(SqlRowSet rs){
        Review review = new Review();
        review.setReview_id(rs.getInt("review_id"));
        review.setGame_id(rs.getInt("game_id"));
        review.setUser_id(rs.getInt("user_id"));
        review.setReview_title(rs.getString("review_title"));
        review.setReview_text(rs.getString("review_text"));
        return review;
    }

    private Rating mapRowToRating(SqlRowSet rs) {
        Rating rating = new Rating();
        rating.setRating_id(rs.getInt("rating_id"));
        rating.setRating_score(rs.getInt("rating_score"));
        rating.setUser_id(rs.getInt("user_id"));
        rating.setGame_id(rs.getInt("game_id"));
        rating.setGame_title(rs.getString("game_title"));
        return rating;
    }



}
