package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class HeadingWidget extends Widget{
    private String size;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
