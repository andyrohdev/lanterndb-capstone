package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class GamesCollections {

    private int gameCollectionId;
    private String title;

    private String coverImageURL;

    private String genre;

    private List<GamesCollections> wishList;
    private List<GamesCollections> currentlyPlaying;
    private List<GamesCollections> havePlayed;

    public GamesCollections() {
    }

    public GamesCollections(int gameCollectionId, String title, String coverImageURL, String genre, List<GamesCollections> wishList, List<GamesCollections> currentlyPlaying, List<GamesCollections> havePlayed) {
        this.gameCollectionId = gameCollectionId;
        this.title = title;
        this.coverImageURL = coverImageURL;
        this.genre = genre;
        this.wishList = new ArrayList<>();
        this.currentlyPlaying = new ArrayList<>();
        this.havePlayed = new ArrayList<>();
    }
    public int getGameId() {
        return gameCollectionId;
    }

    public void setGameId(int gameId) {
        this.gameCollectionId = gameId;
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
    //======================================= Getters/Setters For Playing Info


    public List<GamesCollections> getWishList() {
        return wishList;
    }

    public void setWishList(List<GamesCollections> wishList) {
        this.wishList = wishList;
    }

    public List<GamesCollections> getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(List<GamesCollections> currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public List<GamesCollections> getHavePlayed() {
        return havePlayed;
    }

    public void setHavePlayed(List<GamesCollections> havePlayed) {
        this.havePlayed = havePlayed;
    }
}
