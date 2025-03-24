package com.example.demo.DTO;

import com.example.demo.model.Course;
import com.example.demo.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private String userId;

    private String username;

    private String password;

    private String fullName;

    private String email;

    public UserResponse(String userId, String username, String password, String fullName, String email, String phone, Role role, List<Course> purchasedCourses) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.coursesPurchased = purchasedCourses;
    }

    private String phone;

    private Role role;

    private List<Course> coursesPurchased;
}
