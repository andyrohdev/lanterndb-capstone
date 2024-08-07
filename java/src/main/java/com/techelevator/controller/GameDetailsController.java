package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.Review;
import com.techelevator.model.User;
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
    public List<Review> getReviewById(Principal principal, @PathVariable int id){
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        List<Review> reviewList= jdbcReviewDao.getReviewsByGameId(id, userId );

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




}
