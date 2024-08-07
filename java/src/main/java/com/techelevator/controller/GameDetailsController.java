package com.techelevator.controller;

import com.techelevator.dao.JdbcCollectionListDao;
import com.techelevator.dao.JdbcReviewDao;
import com.techelevator.dao.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameDetailsController {
    @Autowired
    private JdbcCollectionListDao jdbcCollectionListDao;
    @Autowired
    private JdbcReviewDao jdbcReviewDao;




}
