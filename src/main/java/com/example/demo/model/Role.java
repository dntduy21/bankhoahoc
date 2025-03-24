package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @Column(nullable = false, length = 50)
    private String roleId;

    @Column(nullable = false, length = 100, unique = true)
    private String roleName;

    // Getter và Setter

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
