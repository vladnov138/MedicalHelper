package com.example.medicalhelper;

public class Note {

    private int id;
    private String title;
    private String description;
    private int type;
    private String date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Note(int id, String title, String description, int type, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
    }
}
