package com.example.appchachi.Announcements;

import java.util.List;

public class Announcement {
    private String from;
    private String message;
    private List<String> recipients;
    private long timestamp;

    // Required no-argument constructor
    public Announcement() {
    }

    public Announcement(String from, String message, List<String> recipients) {
        this.from = from;
        this.message = message;
        this.recipients = recipients;
        this.timestamp = System.currentTimeMillis();
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}