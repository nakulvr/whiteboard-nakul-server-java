package com.example.whiteboardfall2018serverjava.models;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String title;
    private List<Module> modules = new ArrayList<Module>();

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Course(String title) {
        this.title = title;
    }

    public Course(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Course() {
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
}