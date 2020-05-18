package com.fennec.dailynews.entity;

public class User {

    public int id;
    public String name;
    public String email;
    public String password;
    public String status;
    public String created;
    public String modified;

    public String city;
    public int sexe;

    public User() {
    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(String name, String email, String password, String status, String created, String modified) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.created = created;
        this.modified = modified;
    }

    public User(int id, String name, String email, String password, String status, String created, String modified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.created = created;
        this.modified = modified;
    }
}
