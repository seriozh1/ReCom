package com.s.samsungitschool.recom00.model;


import java.util.Date;

public class ProblemPoint {

    private long id;
    private double latitude;
    private double longitude;
    private int rating;
    private int quantity;
    private Date creationDate;

    public ProblemPoint(double latitude, double longitude) {
        this(latitude, longitude, 0);
    }

    private ProblemPoint(double latitude, double longitude, int rating) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.creationDate = new Date();

    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRating() {
        return rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
