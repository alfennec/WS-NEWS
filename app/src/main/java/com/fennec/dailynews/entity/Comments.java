package com.fennec.dailynews.entity;

public class Comments {

    public int id;
    public int id_user;
    public int id_news;
    public String message;
    public String created;
    public String modified;

    public Comments() {
    }

    public Comments(int id_user, int id_news, String message, String created, String modified) {
        this.id_user = id_user;
        this.id_news = id_news;
        this.message = message;
        this.created = created;
        this.modified = modified;
    }

    public Comments(int id, int id_user, int id_news, String message, String created, String modified) {
        this.id = id;
        this.id_user = id_user;
        this.id_news = id_news;
        this.message = message;
        this.created = created;
        this.modified = modified;
    }
}
