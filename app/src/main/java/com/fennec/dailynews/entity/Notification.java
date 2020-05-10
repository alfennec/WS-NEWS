package com.fennec.dailynews.entity;

public class Notification {

    public int id;
    public String title;
    public String message;
    public String url_notification;
    public String created;
    public String modified;

    public Notification() {
    }

    public Notification(String title, String message, String url_notification, String created, String modified) {
        this.title = title;
        this.message = message;
        this.url_notification = url_notification;
        this.created = created;
        this.modified = modified;
    }

    public Notification(int id, String title, String message, String url_notification, String created, String modified) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.url_notification = url_notification;
        this.created = created;
        this.modified = modified;
    }
}
