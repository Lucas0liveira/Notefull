package com.example.notefull;

import java.util.Date;

public class Note {
    private long id = -1;
    private String title, body, lat, lg, date;

    public Note(long id, String title, String body, String lat, String lg, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public Note() {

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setId(long id ) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
