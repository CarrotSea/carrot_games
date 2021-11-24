package com.alex.carrot_games.business;

public class User {
    private String id;
    private String password;

    public User() {
        id = "";
        password = "";
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
