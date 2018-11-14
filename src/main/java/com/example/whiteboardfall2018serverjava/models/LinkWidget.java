package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class LinkWidget extends Widget{
    private String linkTitle;

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }
}
