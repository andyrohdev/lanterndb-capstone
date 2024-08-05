package com.techelevator.model;

public class CollectionList {

    private int collection_list_id;
    private int user_id;
    private int collection_id;
    private String title;
    private String genre;


    public int getCollection_list_id() {
        return collection_list_id;
    }

    public void setCollection_list_id(int collection_list_id) {
        this.collection_list_id = collection_list_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
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
