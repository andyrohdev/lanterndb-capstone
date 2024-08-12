package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class AdminController {

    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Autowired
    private CollectionListDao collectionListDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private JdbcCommentDao jdbcCommentDao;
    @Autowired
    private RatingDao ratingDao;


    public AdminController(JdbcCollectionListDao jdbcCollectionListDao, JdbcUserDao jdbcuserDao) {
        this.jdbcCollectionListDao = jdbcCollectionListDao;
        this.jdbcUserDao = jdbcuserDao;
    }

    @GetMapping("/admin")
    public List<User> fetchListOfAllUsers(){
        List<User> foundUsers = jdbcUserDao.getUsers();
        return foundUsers;
    }

    @DeleteMapping("/admin")
    public void deleteUser(@Valid @RequestBody User user) {
        userDao.deleteUser(user.getId());
    }

    @GetMapping("/admin/user/collections")
    public List<CollectionList> fetchCollectionsForSpecificUser(
            @RequestParam(value = "collection_id") Integer collectionId,
            @RequestParam(value = "user_id") Integer userId ) {
        CollectionList collectionList = new CollectionList();
        collectionList.setUser_id(userId);
        collectionList.setCollection_id(collectionId);

       List<CollectionList> specificCollections = collectionListDao.adminFetchCollectionByType(collectionList);

       return specificCollections;
    }

    @GetMapping("/admin/user/reviews")
    public List<Review> fetchReviewsForSpecificUser(
            @RequestParam(value = "user_id") Integer userId ) {
        Review review = new Review();
        review.setUser_id(userId);

        List<Review> specificReviews = reviewDao.adminFetchReviewsForSpecificUser(review);

        return specificReviews;
    }
    @GetMapping("/admin/user/comments")
    public List<Comment> getCommentsForSpecificUser(@RequestParam(value = "user_id") int userId){
        Comment comment = new Comment();
        comment.setUser_id(userId);

        List<Comment> specificComments = commentDao.adminGetCommentsForSpecificUser(comment);

        return specificComments;
    }

    @DeleteMapping("/admin/comments")
    public void adminDeleteCommentById(@Valid @RequestBody Comment comment){
        commentDao.adminDeleteComment(comment);
    }
    @DeleteMapping("/admin/reviews")
    public void adminDeleteReviewsById(@Valid @RequestBody Review review){
        reviewDao.adminDeleteReview(review);
    }
    @DeleteMapping("/admin/ratings")
    public void adminDeleteRatingsById(@Valid @RequestBody Rating rating){
        ratingDao.adminDeleteRatings(rating);
    }

}
