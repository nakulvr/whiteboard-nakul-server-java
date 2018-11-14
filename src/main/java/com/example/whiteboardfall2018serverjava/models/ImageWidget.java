package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class ImageWidget extends Widget{
    private String linkText;

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }
}
