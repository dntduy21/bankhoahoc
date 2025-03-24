package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Course {

    @Id
    @Column(nullable = false, length = 50)
    private String courseId;

    @Column(nullable = false, length = 255)
    private String courseName;

    @Column(nullable = false)
    private Long price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String image;

    // Quan hệ với User (người tạo khóa học)
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User creator;

    // Quan hệ với CourseCategory
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private CourseCategory category;

    @Column(nullable = false)
    private LocalDate creationDate;

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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public CourseCategory getCategory() {
        return category;
    }

    public void setCategory(CourseCategory category) {
        this.category = category;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Column
    private LocalDate updateDate;
}
