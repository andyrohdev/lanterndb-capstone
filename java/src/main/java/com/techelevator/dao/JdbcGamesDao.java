package com.techelevator.dao;

import com.techelevator.model.Games;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGamesDao implements GamesDao {

    private List<Games> games;
    public JdbcGamesDao() {

        games = new ArrayList<>();

        games.add(new Games(0,"fornite", "weee", "shooter"));

    }

    @Override
    public List<Games> getGames() {
        return games;
    }
}
