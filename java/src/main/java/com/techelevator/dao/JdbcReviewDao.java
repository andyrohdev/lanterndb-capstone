package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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


    @Override
    public List <Review> addReview(Review review, int userId) {
        List<Review> addReviewToList = null;
        String sql = "INSERT INTO reviews (game_id, user_id, review_title, review_text)\n" +
                "VALUES (?, ?, ?, ?)\n" +
                "RETURNING game_id";

        try{
            int newId = jdbcTemplate.queryForObject(sql, int.class, review.getGame_id(), userId, review.getReview_title(), review.getReview_text());
            addReviewToList = getReviewsByGameId(newId);

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
    public List<Review> getProfileReviews(int user_id) {
        List<Review> reviewList = new ArrayList<>();
        String sql = "SELECT review_id, game_id, user_id, review_title, review_text FROM reviews WHERE user_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
            while(results.next()){
                reviewList.add(mapRowToReview(results));
            }
        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return reviewList;
    }

    @Override
    public List<Review> updateAndEditReview(Review review, int user_id) {
        List<Review> updateReviews = null;
        String sql = "UPDATE reviews\n" +
                "SET review_title = ?, review_text = ?\n" +
                "WHERE user_id = ? AND game_id = ?;";
        try{
            int rowsUpdate = jdbcTemplate.update(sql, review.getReview_title(), review.getReview_text(), review.getUser_id(), review.getGame_id());
            if(rowsUpdate == 0){
                throw new DaoException("Zero rows affected, expected at least one!");
            }
            else{
                updateReviews = getProfileReviews(review.getUser_id());
            }
        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

        return updateReviews;
    }


//    @Override
//    public int deleteGameFromACollection(int collection_list_id) {
//        int rows = 0;
//        String sql = "DELETE FROM collection_list WHERE collection_list_id = ?;";
//        try{
//            rows = jdbcTemplate.update(sql, collection_list_id);
//        }
//        catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//        return rows;
//    }

    @Override
    public int deleteMyReview(Review review, int user_id) {

        int rows = 0;
        String sql = "DELETE FROM reviews WHERE review_id = ? AND user_id = ?;";
        try{
            rows = jdbcTemplate.update(sql, review.getReview_id(), review.getUser_id());
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rows;

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
}
