package com.s.samsungitschool.recom00.model;


public class ProblemPoint {
    String lat, lng;
    int quantity;

    public ProblemPoint() {

    }

    public ProblemPoint(String lat, String lng, int quantity) {
        this.lat = lat;
        this.lng = lng;
        this.quantity = quantity;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
