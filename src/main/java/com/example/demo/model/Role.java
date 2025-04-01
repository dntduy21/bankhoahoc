package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @Column(nullable = false, length = 50)
    private String roleId;

    @Column(nullable = false, length = 100, unique = true)
    private String roleName;

}
