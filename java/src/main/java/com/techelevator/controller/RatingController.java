package com.techelevator.controller;

import com.techelevator.dao.JdbcRatingDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.RatingDao;
import com.techelevator.model.CollectionList;
import com.techelevator.model.Rating;
import com.techelevator.model.Review;
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
public class RatingController {
    @Autowired
    private RatingDao ratingDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;
    @Autowired
    private JdbcRatingDao jdbcRatingDao;

    @GetMapping("/rating")
    public Rating getRatingById(@RequestBody Rating rating) {
        Rating currentRating = ratingDao.getRatingById(rating);

        return currentRating;
    }

    @GetMapping("/ratings/{id}")
    public List<Rating> fetchRatingsByGameId(@PathVariable int id) {
        List<Rating> foundRatings = ratingDao.fetchRatingsByGameId(id);

        if(foundRatings == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ratings could not be found");
        }

        return foundRatings;
    }

    @PutMapping("/user/ratings")
    public List<Rating> updateRating(@Valid @RequestBody Rating rating, Principal principal) {
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        rating.setUser_id(userId);

        return ratingDao.updateRatingById(rating);

    }

    @GetMapping("/user/ratings/{id}")
    public List <Rating> getProfileRatings(@PathVariable int id) {
        List<Rating> ratings = jdbcRatingDao.fetchProfileRatings(id);
        return ratings;
    }


    @PostMapping("/rating")
    public Rating addRating(@RequestBody Rating rating) {

        Rating addedRating = ratingDao.addRating(rating);

        return addedRating;
    }

    @DeleteMapping("/user/ratings")
    public void deleteMyRating(@Valid @RequestBody Rating rating, Principal principal) {
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        rating.setUser_id(userId);

        ratingDao.deleteMyRating(rating);
    }

//    @DeleteMapping("/user/reviews")
//    public void deleteMyReview(@Valid @RequestBody Review review, Principal principal) {
//
//        String userName = principal.getName();
//        User user = jdbcUserDao.getUserByUsername(userName);
//        int userId = user.getId();
//
//        review.setUser_id(userId);
//
//        if(reviewDao.getProfileReviews(review.getReview_id()) == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        reviewDao.deleteMyReview(review, userId);
//    }

}
