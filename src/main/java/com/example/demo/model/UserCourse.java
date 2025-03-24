package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    // Getter và Setter

    public UserCourseId getId() {
        return id;
    }

    public void setId(UserCourseId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}