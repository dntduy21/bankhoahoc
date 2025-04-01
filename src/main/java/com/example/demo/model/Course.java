package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    @Column
    private LocalDate updateDate;
}
