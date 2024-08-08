package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.CollectionList;
import com.techelevator.model.Review;
import com.techelevator.model.User;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.DocFlavor;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
public class GameDetailsController {
    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcReviewDao jdbcReviewDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Autowired
    private ReviewDao reviewDao;

    @GetMapping("/reviews/{id}")
    public List<Review> getReviewById(@PathVariable int id){

        List<Review> reviewList= jdbcReviewDao.getReviewsByGameId(id);

        if(reviewList == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        return reviewList;
    }

    @PostMapping("/reviews")
    public List<Review> addReview(@Valid @RequestBody Review review, Principal principal){
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        review.setUser_id(userId);
        return reviewDao.addReview(review, userId);
    }

    @GetMapping("/user/reviews/{id}")
    public List<Review> getProfileReviews(@PathVariable int id){

        List<Review> reviews = jdbcReviewDao.getProfileReviews(id);
        return reviews;

    }

    @PutMapping("/user/reviews")
    public List<Review> updateAndEditReview(@Valid @RequestBody Review review, Principal principal){
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        review.setUser_id(userId);
        return reviewDao.updateAndEditReview(review, userId);
    }

//    @DeleteMapping("/collections")
//    public void deleteGameFromACollection(@Valid @RequestBody CollectionList collectionList){
//        if(collectionListDao.fetchCollectionById(collectionList.getCollection_list_id()) == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        collectionListDao.deleteGameFromACollection(collectionList.getCollection_list_id());
//    }

    @DeleteMapping("/user/reviews")
    public void deleteMyReview(@Valid @RequestBody Review review, Principal principal) {

        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        review.setUser_id(userId);

        if(reviewDao.getProfileReviews(review.getReview_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        reviewDao.deleteMyReview(review, userId);
    }








}
