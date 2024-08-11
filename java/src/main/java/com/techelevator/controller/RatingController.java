package com.techelevator.controller;

import com.techelevator.dao.RatingDao;
import com.techelevator.model.CollectionList;
import com.techelevator.model.Rating;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class RatingController {
    @Autowired
    private RatingDao ratingDao;

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

//    @GetMapping("/collections/{id}")
//    public List<CollectionList> fetchCollectionListById(
//            Principal principal,
//            @PathVariable int id){
//
//        if (principal == null) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
//        }
//
//        String userName = principal.getName(); // Get the username from the Principal
//        User user = jdbcUserDao.getUserByUsername(userName);
//        int userId = user.getId();
//
//        List<CollectionList> foundCollection = jdbcCollectionListDao.fetchCollectionByType(id, userId);
//
//        if (foundCollection == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
//        }
//
//        return foundCollection;
//    }

    @PostMapping("/rating")
    public Rating addRating(@RequestBody Rating rating) {

        Rating addedRating = ratingDao.addRating(rating);

        return addedRating;
    }

}
