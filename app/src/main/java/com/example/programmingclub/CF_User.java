package com.example.programmingclub;

public class CF_User {

    private String handle;
    private int rating;
    private String country;

    public CF_User(String handle, int rating, String country) {
        this.handle = handle;
        this.rating = rating;
        this.country = country;
    }

    public String getHandle() {
        return handle;
    }

    public int getRating() {
        return rating;
    }

    public String getCountry() {
        return country;
    }
}
