package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class ListWidget extends Widget{
    private String options;

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
