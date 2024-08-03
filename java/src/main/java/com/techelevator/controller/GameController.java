package com.techelevator.controller;

import com.techelevator.dao.GamesDao;
import com.techelevator.model.Games;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.security.Principal;
import java.util.List;
@CrossOrigin
@RestController
public class GameController {
private String API_URL = "http://localhost:8080/games";
private String API_KEY = "916f7023c320440daebd11eb40e66e2e";
private RestTemplate restTemplate;
private GamesDao gamesDao;



    @RequestMapping(path="/games", method = RequestMethod.GET)
    public List<Games> fetchAllGames (){
        List<Games> fetchedGames = gamesDao.getGames();
        return fetchedGames;
    }

//    @GetMapping(value = "/clientcallgames")
//    public List<Games> retrieveAllGames(){
//        String uri = API_URL;
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        return result;
//    }

    }
