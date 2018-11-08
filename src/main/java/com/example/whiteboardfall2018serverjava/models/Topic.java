package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @OneToMany(mappedBy = "topic")
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
