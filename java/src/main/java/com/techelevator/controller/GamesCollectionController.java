package com.techelevator.controller;

import com.techelevator.dao.GamesCollectionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.GamesCollections;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class GamesCollectionController {

    @Autowired
    private GamesCollectionDao gamesCollectionDao;
    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/collection/{id}")
    public GamesCollections fetchCollectionById(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        return gamesCollectionDao.fetchCollectionById(user.getId());
    }


    }
