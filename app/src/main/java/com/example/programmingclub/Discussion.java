package com.example.programmingclub;

class Discussion {
    private String id;
    private String user;
    private String message;
    private long timestamp;

    public Discussion() {
    }

    public Discussion(String id, String user, String message, long timestamp) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}