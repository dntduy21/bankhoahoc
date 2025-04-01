package com.example.demo.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponse {
    private String courseId;
    private String courseName;
    private Long price;
    private String description;
    private String image;
    private String creationDate;
    private String updateDate;
    private String creatorUsername;
    private String creatorFullName;
    private CreaterResponse createrResponse;
    private String roleName;
    private String category;

    public CourseResponse(String courseId, String courseName, Long price, String description, String image, String creationDate, String updateDate, CreaterResponse createrResponse, String category) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.price = price;
        this.description = description;
        this.image = image;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.creatorUsername = createrResponse.getUsername();
        this.creatorFullName = createrResponse.getFullName();
        this.createrResponse = createrResponse;
        this.roleName = createrResponse.getRoleName();
        this.category = category;
    }
}

