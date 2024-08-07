package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao{

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final JdbcUserDao jdbcUserDao;

    @Autowired
    private UserDao userDao;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public List <Review> getReviewsByGameId(int game_id, int user_id) {
        List<Review> reviewsList = new ArrayList<>();
        String sql = "SELECT review_id, game_id, review_text FROM reviews WHERE game_id = ? AND user_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, game_id, user_id);
                    if(results.next()){
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

    @Override
    public List <Review> addReview(Review review, int user_id) {
        List<Review> addReviewToList = null;
        String sql = "INSERT INTO reviews (game_id, user_id, review_text)\n" +
                "VALUES (?, ?, ?)\n" +
                "RETURNING game_id";

        try{
            int newId = jdbcTemplate.queryForObject(sql, int.class, review.getGame_id(), review.getUser_id(), review.getReview_text());
            addReviewToList = getReviewsByGameId(newId, user_id);

        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return addReviewToList;
    }

    @Override
    public Review updateAndEditReview(Review review) {
        return null;
    }

    @Override
    public Review deleteReview(int review_id) {
        return null;
    }

    private Review mapRowToReview(SqlRowSet rs){
        Review review = new Review();
        review.setReview_id(rs.getInt("review_id"));
        review.setGame_id(rs.getInt("game_id"));
        review.setReview_text(rs.getString("review_text"));
        return review;
    }
}
