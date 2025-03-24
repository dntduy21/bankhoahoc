package com.example.demo.DTO;

import java.util.List;

public class RegistrationRequest {
    private String username;
    private List<String> listCoursesId;

    // Getters và Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getListCoursesId() {
        return listCoursesId;
    }

    public void setListCoursesId(List<String> listCoursesId) {
        this.listCoursesId = listCoursesId;
    }
}
