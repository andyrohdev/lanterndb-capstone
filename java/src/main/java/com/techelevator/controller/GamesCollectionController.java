package com.techelevator.controller;

import com.techelevator.dao.GamesCollectionDao;
import com.techelevator.dao.JdbcGamesCollecitonDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.GamesCollections;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@CrossOrigin
@RestController
public class GamesCollectionController {

    @Autowired
    private JdbcGamesCollecitonDao jdbcGamesCollectionDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    public GamesCollectionController(JdbcGamesCollecitonDao jdbcgamesCollectionDao, JdbcUserDao jdbcuserDao) {
        this.jdbcGamesCollectionDao = jdbcgamesCollectionDao;
        this.jdbcUserDao = jdbcuserDao;
    }

    public GamesCollectionController() {

    }
    //    @GetMapping(value = "/collection/{id}")
//    public GamesCollections fetchCollectionById(Principal principal @PathVariable int id) {
//        String userName = principal.getName();
//        User user = userDao.getUserByUsername(userName);
//        GamesCollections foundCollection = gamesCollectionDao.fetchCollectionById(id);
//        if(foundCollection == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        return foundCollection(gamesCollectionDao.fetchCollectionById(user.getId()));
//    }

    @GetMapping("/collections/{id}")
    public GamesCollections fetchCollectionById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int id){

        String userName = userDetails.getUsername();
        User user = jdbcUserDao.getUserByUsername(userName);

        if (user == null) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        }

        GamesCollections foundCollection = jdbcGamesCollectionDao.fetchCollectionById(id);

        if(foundCollection == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }

        return foundCollection;

    }



    }
