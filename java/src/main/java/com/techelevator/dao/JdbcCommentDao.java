package com.techelevator.dao;

import com.techelevator.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
        return null;
    }

    @Override
    public Comment getCommentById(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByGameId(int game_id) {
        return null;
    }

    @Override
    public List<Comment> getProfileComments(int user_id) {
        return null;
    }

    @Override
    public List<Comment> updateCommentsById(Comment comment) {
        return null;
    }

    @Override
    public int deleteComment(Comment comment) {
        return 0;
    }
}
