package com.example.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private int dayPublish;
    private boolean type;

    public Book(int id, String name, int dayPublish, boolean type) {
        this.id = id;
        this.name = name;
        this.dayPublish = dayPublish;
        this.type = type;
    }

    public Book(String name, int dayPublish, boolean type) {
        this.name = name;
        this.dayPublish = dayPublish;
        this.type = type;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDayPublish() {
        return dayPublish;
    }

    public void setDayPublish(int dayPublish) {
        this.dayPublish = dayPublish;
    }

    public boolean isNovel() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
