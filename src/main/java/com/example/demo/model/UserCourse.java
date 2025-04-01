package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class UserCourse {
    @EmbeddedId
    private UserCourseId id;

    @ManyToOne
    @JoinColumn(
            name = "userId",
            referencedColumnName = "userId",
            insertable = false,
            updatable = false
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "courseId",
            referencedColumnName = "courseId",
            insertable = false,
            updatable = false
    )
    private Course course;

    @Column
    private LocalDate registrationDate;
}