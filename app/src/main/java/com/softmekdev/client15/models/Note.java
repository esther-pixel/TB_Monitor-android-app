package com.softmekdev.client15.models;

public class Note {
    private String title, description;
    private int priority;

    public Note() {
        //Required empty constructor
    }

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
