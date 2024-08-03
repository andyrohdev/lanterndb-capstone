package com.techelevator.dao;

import com.techelevator.model.GamesCollections;
import org.springframework.stereotype.Component;

@Component
public class JdbcGamesCollecitonDao implements GamesCollectionDao{

    @Override
    public GamesCollections fetchCollectionListById(int gameCollectionId) {
        return null;
    }
}
