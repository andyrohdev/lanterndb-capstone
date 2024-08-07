package com.techelevator.controller;

import com.techelevator.dao.CollectionListDao;
import com.techelevator.dao.JdbcCollectionListDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Autowired
    private CollectionListDao collectionListDao;
    @Autowired
    private UserDao userDao;

    public UserController(JdbcCollectionListDao jdbcCollectionListDao, JdbcUserDao jdbcuserDao) {
        this.jdbcCollectionListDao = jdbcCollectionListDao;
        this.jdbcUserDao = jdbcuserDao;
    }

    @GetMapping("/admin")
    public List<User> fetchListOfAllUsers(){
        List<User> foundUsers = jdbcUserDao.getUsers();
        return foundUsers;
    }

    @DeleteMapping("/admin")
    public void deleteUser(@Valid @RequestBody User user) {
        userDao.deleteUser(user.getId());
    }
}
