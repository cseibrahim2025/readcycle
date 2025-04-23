package com.ibrahimcodelab.readcycle.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    private User user;

    @SerializedName("token")
    private String token;

    public static class User {

        @SerializedName("id")
        private int id;

        @SerializedName("student_id")
        private String studentId;

        @SerializedName("name")
        private String name;

        @SerializedName("university_name")
        private String universityName;

        @SerializedName("department")
        private String department;

        @SerializedName("year")
        private String year;

        @SerializedName("email")
        private String email;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
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
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

