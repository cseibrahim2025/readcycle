package com.ibrahimcodelab.readcycle.dao;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    private String name;

    @SerializedName("university_name")
    private String universityName;

    private String department;
    private String year;
    private String email;
    private String password;

    public RegisterRequest(String name, String universityName,
                           String department, String year, String email, String password) {
        this.name = name;
        this.universityName = universityName;
        this.department = department;
        this.year = year;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
