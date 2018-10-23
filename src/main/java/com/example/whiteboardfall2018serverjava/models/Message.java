package com.example.whiteboardfall2018serverjava.models;
import java.util.Date;

public class Message {
    private String text = "Default text";
    private Date createdAt = new Date();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
