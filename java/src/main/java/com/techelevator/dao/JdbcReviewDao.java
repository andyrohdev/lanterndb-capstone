package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcReviewDao implements ReviewDao{

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final JdbcUserDao jdbcUserDao;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public Review getReviewsById(int review_id) {
        Review reviews = null;
        String sql = "SELECT review_id, game_id, review_text FROM reviews WHERE review_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, review_id);
                    if(results.next()){
                        reviews =  mapRowToReview(results);
                    }


        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return reviews;
    }

    @Override
    public Review addReview(Review review) {
        return null;
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
