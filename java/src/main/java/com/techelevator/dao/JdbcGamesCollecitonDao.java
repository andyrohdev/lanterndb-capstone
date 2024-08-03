package com.techelevator.dao;

import com.techelevator.model.GamesCollections;
import org.springframework.stereotype.Component;

@Component
public class JdbcGamesCollecitonDao implements GamesCollectionDao{
    //    private List<Games> games;
//    @Autowired
//    public JdbcGamesDao() {
//
//        games = new ArrayList<>();
//
//        games.add(new Games(0,"fornite", "weee", "shooter"));
//
//    }
//
//    @Override
//    public List<Games> getGames() {
//        return games;
//    }


    @Override
    public GamesCollections fetchCollectionById(int gameCollectionId) {
        return null;
    }



}
