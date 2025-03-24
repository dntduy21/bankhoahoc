package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class CourseCategory {
    @Id
    @Column(nullable = false, length = 50)
    private String categoryId;

    @Column(length = 100)
    private String categoryName;

    // Getter và Setter

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
