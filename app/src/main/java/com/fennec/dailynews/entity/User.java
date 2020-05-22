package com.fennec.dailynews.entity;

public class User {

    public int id;
    public String fname;
    public String lname;
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

    public User(String fname, String lname, String email, String password, String status) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(String fname, String lname, String email, String password, String status, String created, String modified) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.status = status;
        this.created = created;
        this.modified = modified;
    }

    public User(int id, String fname, String lname, String email, String password, String status, String created, String modified) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.status = status;
        this.created = created;
        this.modified = modified;
    }
}
