package com.techelevator.model;

public class CollectionList {

    private int collectionListId;
    private int userId;
    private int collectionId;
    private String title;
    private String genre;


    public int getCollectionListId() {
        return collectionListId;
    }

    public void setCollectionListId(int collectionListId) {
        this.collectionListId = collectionListId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
