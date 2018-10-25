package com.example.whiteboardfall2018serverjava.models;

public class Widget {
    private int id = User.autoIncrement++;
    private String title;

    public Widget() {
    }

    public Widget(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
