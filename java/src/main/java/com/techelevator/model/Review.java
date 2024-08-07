package com.techelevator.model;

public class Review {
    private int review_id;
    private int game_id;

    private String review_text;

    public Review(int review_id, int game_id, String review_text) {
        this.review_id = review_id;
        this.game_id = game_id;
        this.review_text = review_text;
    }

    public Review() {

    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }
}
