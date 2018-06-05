package com.s.samsungitschool.recom00.model;


public class Note {

    private long id;
    private String text;
    private ProblemPoint point;

    public Note() {
        this.text = text;
        this.point = point;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ProblemPoint getPoint() {
        return point;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPoint(ProblemPoint point) {
        this.point = point;
    }
}
