package com.example.programmingclub;

public class Notice {
    private String headline;
    private String description;
    private String link;
    private long timestamp;

    public Notice() {
        // Default constructor for Firebase
    }

    public Notice(String headline, String description, String link, long timestamp) {
        this.headline = headline;
        this.description = description;
        this.link = link;
        this.timestamp = timestamp;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
