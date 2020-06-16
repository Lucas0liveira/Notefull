package com.example.notefull;

public class Note {
    private String title, body, timer, lat, lg;

    public Note(String title, String body, String timer, String lat, String lg) {
        this.title = title;
        this.body = body;
        this.timer = timer;
        this.lat = lat;
        this.lg = lg;
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
}
