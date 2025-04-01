package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseCategory {
    @Id
    @Column(nullable = false, length = 50)
    private String categoryId;

    @Column(length = 100)
    private String categoryName;
}
