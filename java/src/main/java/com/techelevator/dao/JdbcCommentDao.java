package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Comment;
import com.techelevator.model.Rating;
import com.techelevator.model.Review;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCommentDao implements CommentDao{

    private final JdbcTemplate jdbcTemplate;

    private final JdbcUserDao jdbcUserDao;

    private Comment comment;

    public JdbcCommentDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }


    @Override
    public Comment addComment(Comment comment) {
        Comment comments = null;
        String sql = "INSERT INTO comments (review_id, game_id, user_id, comment_text) " +
                    "VALUES (?, ?, ?, ?) " +
                    "RETURNING comment_id;";
        int newId = jdbcTemplate.queryForObject(sql, int.class, comment.getReview_id(), comment.getGame_id(), comment.getUser_id(), comment.getComment_text());
        comment.setComment_id(newId);
        comments = getCommentById(comment);
        return comments;
    }

    @Override
    public Comment getCommentById(Comment comment) {
        Comment comments = null;

        String sql = "SELECT comment_id, review_id, game_id, user_id, comment_text FROM comments WHERE comment_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, comment.getComment_id());
                    if(results.next()){
                        comments = mapToRowComments(results);
                    }
        }
        catch (
        CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
        }

        return comments;
    }

    @Override
    public List<Comment> getCommentsByGameId(int game_id) {
        List<Comment> commentsList = new ArrayList<>();

        String sql = "SELECT comment_id, review_id, game_id, user_id, comment_text FROM comments WHERE game_id = ?\n";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, game_id);
            while (results.next()) {
                commentsList.add( mapToRowComments(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return commentsList;
    }

    @Override
    public List<Comment> getProfileComments(int user_id) {
        List<Comment> commentList = new ArrayList<>();
        String sql = "SELECT comment_id, review_id, game_id, user_id, comment_text FROM comments WHERE user_id = ?";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
            while(results.next()){
                commentList .add(mapToRowComments(results));
            }
        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }


        return commentList;
    }

    @Override
    public List<Comment> updateCommentsById(Comment comment) {
        List<Comment> updateComments = null;
        String sql = "UPDATE comments SET comment_text = ? WHERE comment_id = ? AND user_id = ?;";
        try{
            int rowsUpdate = jdbcTemplate.update(sql, comment.getComment_text(), comment.getComment_id(), comment.getUser_id());
            if(rowsUpdate == 0){
                throw new DaoException("Zero rows affected, expected at least one!");
            }
            else{
                updateComments = getProfileComments(comment.getUser_id());
            }
        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }



        return updateComments;

    }

    @Override
    public int deleteComment(Comment comment) {

        int rows = 0;

        String sql = "Delete FROM comments WHERE comment_id = ? AND user_id = ?;";
        try{
            rows = jdbcTemplate.update(sql, comment.getComment_id(), comment.getUser_id());
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rows;
    }


    @Override
    public List<Comment> adminGetCommentsForSpecificUser(Comment comment) {
        List<Comment> commentList = new ArrayList<>();
        String sql = "SELECT comment_id, review_id, game_id, user_id, comment_text FROM comments WHERE user_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, comment.getUser_id());
            while(results.next()){
                commentList.add(mapToRowComments(results));
            }
        }
        catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return commentList;

    }

    @Override
    public int adminDeleteComment(Comment comment) {
        int rows = 0;

        String sql = "DELETE FROM comments WHERE comment_id = ?;";
        try{
            rows = jdbcTemplate.update(sql, comment.getComment_id());
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to DB!", e);
        }
        catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return rows;
    }
//    @Override
//    public List<Review> adminFetchReviewsForSpecificUser(Review review) {
//        List<Review> reviewList = new ArrayList<>();
//        String sql = "SELECT review_id, game_id, user_id, review_title, review_text FROM reviews WHERE user_id = ?;";
//
//        try{
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, review.getUser_id());
//            while(results.next()){
//                reviewList.add(mapRowToReview(results));
//            }
//        }
//        catch(CannotGetJdbcConnectionException e){
//            throw new DaoException("Unable to connect to DB!", e);
//        }
//        catch(DataIntegrityViolationException e){
//            throw new DaoException("Data integrity violation", e);
//        }
//        return reviewList;
//    }

    public Comment mapToRowComments(SqlRowSet rs) {
        Comment comment = new Comment();
        comment.setComment_id(rs.getInt("comment_id"));
        comment.setReview_id(rs.getInt("review_id"));
        comment.setGame_id(rs.getInt("game_id"));
        comment.setUser_id(rs.getInt("user_id"));
        comment.setComment_text(rs.getString("comment_text"));
        return comment;
    }

}
