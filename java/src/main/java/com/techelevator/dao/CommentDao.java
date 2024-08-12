package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> addComment(Comment comment);

    List<Comment> getCommentByReviewId(Comment comment);
    List<Comment> getCommentsByGameId(int game_id);

    List<Comment> getProfileComments(int user_id);

    List<Comment> updateCommentsById(Comment comment);

    int deleteComment(Comment comment);
    List<Comment> adminGetCommentsForSpecificUser(Comment comment);
    int adminDeleteComment(Comment comment);

}
