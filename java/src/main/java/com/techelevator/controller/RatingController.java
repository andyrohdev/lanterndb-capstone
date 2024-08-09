package com.techelevator.controller;

import com.techelevator.dao.RatingDao;
import com.techelevator.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/rating")
    public Rating addRating(@RequestBody Rating rating) {

        Rating addedRating = ratingDao.addRating(rating);

        return addedRating;
    }

}
