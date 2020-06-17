package com.example.notefull;

import java.util.Date;

public class Note {
    private String title, body, folder, timer, lat, lg, date;

    public Note(String title, String body, String folder, String time, String lat, String lg, String date) {
        this.title = title;
        this.body = body;
        this.folder = folder;
        this.timer = time;
        this.lat = lat;
        this.lg = lg;
        this.date = date;
    }

    public Note() {

    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getFolder() {
        return folder;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFolder(String folder) {
        this.folder = folder;
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
