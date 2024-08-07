package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.User;
import com.techelevator.model.CollectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class GamesCollectionController {

    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Autowired
    private CollectionListDao collectionListDao;
    @Autowired
    private UserDao userDao;

    public GamesCollectionController(JdbcCollectionListDao jdbcCollectionListDao, JdbcUserDao jdbcuserDao) {
        this.jdbcCollectionListDao = jdbcCollectionListDao;
        this.jdbcUserDao = jdbcuserDao;
    }


    @GetMapping("/collections/{id}")
    public List<CollectionList> fetchCollectionListById(
            Principal principal,
            @PathVariable int id){

        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String userName = principal.getName(); // Get the username from the Principal
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        List<CollectionList> foundCollection = jdbcCollectionListDao.fetchCollectionByType(id, userId);

        if (foundCollection == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }

        return foundCollection;
    }

    @PostMapping("/collections")
    public List<CollectionList> addGameToCollectionList(@Valid @RequestBody CollectionList collectionList, Principal principal){
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        collectionList.setUser_id(userId);
        return collectionListDao.addGameToCollection(collectionList, userId);
    }

    @PutMapping("/collections")
    public CollectionList updateGameInCollectionList(@Valid @RequestBody CollectionList collectionList, Principal principal){
        String userName = principal.getName();
        User user = jdbcUserDao.getUserByUsername(userName);
        int userId = user.getId();

        collectionList.setUser_id(userId);
        return collectionListDao.updateCollection(collectionList, userId);
    }
    @DeleteMapping("/collections")
    public void deleteGameFromACollection(@Valid @RequestBody CollectionList collectionList){
        if(collectionListDao.fetchCollectionById(collectionList.getCollection_list_id()) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        collectionListDao.deleteGameFromACollection(collectionList.getCollection_list_id());
    }




}




