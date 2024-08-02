package com.techelevator.model;

public class Games {

    private int gameId = 1;
    private String title = "Fornite";

    private String coverImageURL = "wee";

    private String genre = "Shooter";

    public Games() {
    }

    public Games(int gameId, String title, String coverImageURL, String genre) {
        this.gameId = gameId;
        this.title = title;
        this.coverImageURL = coverImageURL;
        this.genre = genre;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
