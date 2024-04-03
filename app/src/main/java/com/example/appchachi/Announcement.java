package com.example.appchachi;

import java.util.List;

public class Announcement {
    private String from;
    private String message;
    private List<String> recipients; // Add recipients field

    public Announcement(String from, String message, List<String> recipients) {
        this.from = from;
        this.message = message;
        this.recipients = recipients;
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

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
