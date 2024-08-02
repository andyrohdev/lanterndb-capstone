package com.techelevator.controller;

import com.techelevator.dao.GamesDao;
import com.techelevator.model.Games;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;
import java.util.List;

@RestController
public class GameController {

private GamesDao gamesDao;


    @RequestMapping(path="/games", method = RequestMethod.GET)
    public List<Games> fetchAllGames (){
        List<Games> fetchedGames = gamesDao.getGames();
        return fetchedGames;
    }

}
