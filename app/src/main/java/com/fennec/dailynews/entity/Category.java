package com.fennec.dailynews.entity;

public class Category {

    public int id;
    public String name;
    public String image;
    public String created;
    public String modified;


    public Category() {
    }

    public Category(String name, String image, String created, String modified) {
        this.name = name;
        this.image = image;
        this.created = created;
        this.modified = modified;
    }

    public Category(int id, String name, String image, String created, String modified) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.created = created;
        this.modified = modified;
    }
}
