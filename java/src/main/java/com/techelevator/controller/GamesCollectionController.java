package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.User;
import com.techelevator.model.CollectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@CrossOrigin
@RestController
public class GamesCollectionController {

    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    public GamesCollectionController(JdbcCollectionListDao jdbcWishListDao, JdbcUserDao jdbcuserDao) {
        this.jdbcCollectionListDao = jdbcWishListDao;
        this.jdbcUserDao = jdbcuserDao;
    }

    public GamesCollectionController() {

    }


    @GetMapping("/collections/{id}")
    public CollectionList fetchCollectionListById(
            Principal principal,
            @PathVariable int id){

        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String userName = principal.getName(); // Get the username from the Principal
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        CollectionList foundCollection = jdbcCollectionListDao.fetchCollectionByType(id, userId);

        if (foundCollection == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }

        return foundCollection;
    }
}




