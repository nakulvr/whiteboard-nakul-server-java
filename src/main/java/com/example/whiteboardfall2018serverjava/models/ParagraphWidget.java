package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class ParagraphWidget extends Widget{
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
