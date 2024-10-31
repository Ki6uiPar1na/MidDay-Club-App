package com.example.programmingclub;

public class storeUserData {
    private String name, email, password, registrationNumber, session;

    public storeUserData(String name, String email, String password, String registrationNumber, String session) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.session = session;
    }

    //setter functionality
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setSession(String session) {
        this.session = session;
    }


    //data getter functinality


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getSession() {
        return session;
    }
}
