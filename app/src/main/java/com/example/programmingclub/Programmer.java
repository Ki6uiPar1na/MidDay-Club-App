package com.example.programmingclub;

public class Programmer {

    private String name;
    private String session;

    // Constructor
    public Programmer(String name, String session) {
        this.name = name;
        this.session = session;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSession() {
        return session;
    }
}

