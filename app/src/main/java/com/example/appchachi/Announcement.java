package com.example.appchachi;

public class Announcement {
    private String from;
    private String message;

    public Announcement(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}
