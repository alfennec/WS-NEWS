package com.fennec.dailynews.entity;

public class Comments {

    public int id;
    public String id_user;
    public int id_news;
    public String message;
    public String created;
    public String modified;

    public Comments() {
    }

    public Comments(String id_user, int id_news, String message) {
        this.id_user = id_user;
        this.id_news = id_news;
        this.message = message;
    }

    public Comments(String id_user, int id_news, String message, String created, String modified) {
        this.id_user = id_user;
        this.id_news = id_news;
        this.message = message;
        this.created = created;
        this.modified = modified;
    }

    public Comments(int id, String id_user, int id_news, String message, String created, String modified) {
        this.id = id;
        this.id_user = id_user;
        this.id_news = id_news;
        this.message = message;
        this.created = created;
        this.modified = modified;
    }
}
