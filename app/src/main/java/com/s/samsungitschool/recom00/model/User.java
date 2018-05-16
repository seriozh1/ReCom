package com.s.samsungitschool.recom00.model;

public class User {
    private String login, email, password;
    private int points;

    public User() {

    }

    public User(int id, String login, String email, String password, int points) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.points = points;
    }


    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
