package com.example.notefull;

import java.util.Date;

public class Note {
    private long id = -1;
    private String title, body, timer, lat, lg, date;

    public Note(long id, String title, String body, String time, String lat, String lg, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.timer = time;
        this.lat = lat;
        this.lg = lg;
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

    public String getTimer() {
        return timer;
    }

    public String getLat() {
        return lat;
    }

    public String getLg() {
        return lg;
    }

    public String getDate() {
        return date;
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

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
