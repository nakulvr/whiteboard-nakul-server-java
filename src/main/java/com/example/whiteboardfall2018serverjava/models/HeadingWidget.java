package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class HeadingWidget extends Widget{
    private String headingTitle;

    public String getHeadingTitle() {
        return headingTitle;
    }

    public void setHeadingTitle(String headingTitle) {
        this.headingTitle = headingTitle;
    }
}
