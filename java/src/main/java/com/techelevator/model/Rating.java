package com.techelevator.model;

public class Rating {

    private int rating_id;
    private int rating_score;
    private int user_id;
    private int game_id;
    private String game_title;


    public Rating(int rating_id, int rating_score, int user_id, int game_id, String game_title) {
        this.rating_id = rating_id;
        this.rating_score = rating_score;
        this.user_id = user_id;
        this.game_id = game_id;
        this.game_title = game_title;
    }

    public Rating() {

    }

    public int getRating_id() {
        return rating_id;
    }

    public void setRating_id(int rating_id) {
        this.rating_id = rating_id;
    }

    public int getRating_score() {
        return rating_score;
    }

    public void setRating_score(int rating_score) {
        this.rating_score = rating_score;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGame_title() {
        return game_title;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }
}
