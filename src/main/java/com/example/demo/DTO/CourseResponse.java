package com.example.demo.DTO;

import lombok.Data;

@Data
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


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getCreatorFullName() {
        return creatorFullName;
    }

    public void setCreatorFullName(String creatorFullName) {
        this.creatorFullName = creatorFullName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

