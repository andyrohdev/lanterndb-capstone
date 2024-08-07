package com.techelevator.controller;

import com.techelevator.dao.JdbcCollectionListDao;
import com.techelevator.dao.JdbcReviewDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.ReviewDao;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GameDetailsController {
    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcReviewDao jdbcReviewDao;

    @Autowired
    private ReviewDao reviewDao;

    @GetMapping("/reviews/{id}")
    public List<Review> getReviewById(@PathVariable int id){
        List<Review> reviewList= reviewDao.getReviewsByGameId(id);
        if(reviewList == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        return reviewList;
    }


}
