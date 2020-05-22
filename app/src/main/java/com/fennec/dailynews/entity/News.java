package com.fennec.dailynews.entity;

public class News {

    public int id;
    public int id_category;
    public String title;
    public String date_news;
    public String content_type;
    public String description;
    public String news_photo;
    public String news_video;
    public String news_link;
    public String wname;
    public String created;
    public String modified;

    public int nbr_comments;

    public News() {
    }

    public News(int id_category, String title, String date_news, String content_type, String description, String news_photo, String news_video, String news_link, String wname, String created, String modified) {
        this.id_category = id_category;
        this.title = title;
        this.date_news = date_news;
        this.content_type = content_type;
        this.description = description;
        this.news_photo = news_photo;
        this.news_video = news_video;
        this.news_link = news_link;
        this.wname = wname;
        this.created = created;
        this.modified = modified;
    }

    public News(int id, int id_category, String title, String date_news, String content_type, String description, String news_photo, String news_video, String news_link, String wname, String created, String modified) {
        this.id = id;
        this.id_category = id_category;
        this.title = title;
        this.date_news = date_news;
        this.content_type = content_type;
        this.description = description;
        this.news_photo = news_photo;
        this.news_video = news_video;
        this.news_link = news_link;
        this.wname = wname;
        this.created = created;
        this.modified = modified;
    }
}
