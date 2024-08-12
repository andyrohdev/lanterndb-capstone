package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    Comment addComment(Comment comment);

    Comment getCommentById(Comment comment);
    List<Comment> getCommentsByGameId(int game_id);

    List<Comment> getProfileComments(int user_id);

    List<Comment> updateCommentsById(Comment comment);

    int deleteComment(Comment comment);

}
