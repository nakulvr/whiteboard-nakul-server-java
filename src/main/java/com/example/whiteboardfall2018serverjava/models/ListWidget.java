package com.example.whiteboardfall2018serverjava.models;

import javax.persistence.Entity;

@Entity
public class ListWidget extends Widget{
    private String items;
    private Boolean ordered = false;
    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Boolean getOrdered() {
        return ordered;
    }

    public void setOrdered(Boolean ordered) {
        this.ordered = ordered;
    }
}
