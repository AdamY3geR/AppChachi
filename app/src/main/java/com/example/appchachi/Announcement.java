package com.example.appchachi;

/**
 * Class representing an announcement.
 */
public class Announcement {
    private String from;
    private String message;

    /**
     * Constructor for creating an Announcement object.
     *
     * @param from    The sender of the announcement.
     * @param message The content of the announcement.
     */
    public Announcement(String from, String message) {
        this.from = from;
        this.message = message;
    }

    /**
     * Getter method for retrieving the sender of the announcement.
     *
     * @return The sender of the announcement.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Getter method for retrieving the content of the announcement.
     *
     * @return The content of the announcement.
     */
    public String getMessage() {
        return message;
    }
}
