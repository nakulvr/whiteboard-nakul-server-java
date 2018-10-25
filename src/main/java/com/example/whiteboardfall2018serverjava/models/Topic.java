package com.example.whiteboardfall2018serverjava.models;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private int id = User.autoIncrement++;
    private String title;
    private List<Widget> widgets = new ArrayList<Widget>();

    public Topic(String title) {
        this.title = title;
    }

    public Topic() {
    }

    public List<Widget> getWidgets() {
        return widgets;
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

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }
}
