package com.example.programmingclub;

public class User {

    private String email;
    private String name;
    private String password;
    private String registrationNumber;
    private String session;

    public User() {
        // Default constructor for Firebase
    }

    public User(String email, String name, String password, String registrationNumber, String session) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.session = session;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
