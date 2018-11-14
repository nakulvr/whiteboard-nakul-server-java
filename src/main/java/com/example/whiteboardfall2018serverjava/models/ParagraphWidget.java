package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class ParagraphWidget extends Widget{
    private String paragraphText;

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }
}
