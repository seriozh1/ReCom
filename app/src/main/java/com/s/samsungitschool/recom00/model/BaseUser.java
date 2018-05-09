package com.s.samsungitschool.recom00.model;

public class BaseUser {
    private int id;
    private String login, email;
    private int points;
    private String rank;

    public BaseUser() {

    }

    public BaseUser(int id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public int getId() {
        return id;
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

    public String getRank() {
        return rank;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setRank(String rank) {
        this.rank = rank;
    }
}
