package com.example.programmingclub;

public class Teacher {
    private String teacherName;
    private String designation;
    private String phoneNumber;
    private String email;
    private String department;  // Added department field

    // Default constructor
    public Teacher() {
        // Default values or nothing
    }

    // Constructor with parameters (including department)
    public Teacher(String teacherName, String designation, String phoneNumber, String email, String department) {
        this.teacherName = teacherName;
        this.designation = designation;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.department = department;  // Set department
    }

    // Getters and setters for each field
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;  // Getter for department
    }

    public void setDepartment(String department) {
        this.department = department;  // Setter for department
    }
}
