package com.techelevator.model;




public class Comment {

    private int comment_id;
    private int user_id;
    private int game_id;
    private int review_id;
    private String comment_text;

    public Comment(int comment_id, int user_id, int game_id, int review_id, String comment_text) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.review_id = review_id;
        this.comment_text = comment_text;
    }

    public Comment() {

    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
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

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }
}
