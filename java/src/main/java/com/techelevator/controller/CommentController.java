package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.Comment;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class CommentController {

    @Autowired
    private RatingDao ratingDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;
    @Autowired
    private JdbcCommentDao jdbcCommentDao;

    @Autowired
    private ReviewDao reviewDao;


    @PostMapping("/comments")
    public List<Comment> addComment(@RequestBody Comment comment) {

        List<Comment> comments = jdbcCommentDao.addComment(comment);
        return comments;
    }

    @GetMapping("/comments/reviews/{id}")
    public List<Comment> getCommentsByReviewId(@Valid @PathVariable int id) {
        List<Comment> commentList = jdbcCommentDao.getCommentByReviewId(id);

        return commentList;
    }

    @GetMapping("/comments/{id}")
    public List<Comment> getCommentsByGameId(@PathVariable int id) {
        List<Comment> foundComments = jdbcCommentDao.getCommentsByGameId(id);
        if(foundComments == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comments could not be found");
        }

        return foundComments;
    }

    @PutMapping("/user/comments")
    public List<Comment> updateComments(@Valid @RequestBody Comment comment, Principal principal) {
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        comment.setUser_id(userId);

        return jdbcCommentDao.updateCommentsById(comment);

    }

    @GetMapping("/user/comments/{id}")
    public List <Comment> getProfileComments(@PathVariable int id) {
        List<Comment> comments = jdbcCommentDao.getProfileComments(id);
        return comments;
    }

    @DeleteMapping("/user/comments")
    public void deleteMyComments(@Valid @RequestBody Comment comment, Principal principal) {
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        comment.setUser_id(userId);

        jdbcCommentDao.deleteComment(comment);
    }

}
