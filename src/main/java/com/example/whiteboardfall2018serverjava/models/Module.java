package com.example.whiteboardfall2018serverjava.models;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private int id = User.autoIncrement++;
    private String title;
    private List<Lesson> lessons = new ArrayList<Lesson>();

    public Module(String title) {
        this.title = title;
    }

    public Module() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
