package com.example.demo.DTO;

import com.example.demo.model.Course;
import com.example.demo.model.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userId;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phone;

    private Role role;

    private List<Course> coursesPurchased;
}
