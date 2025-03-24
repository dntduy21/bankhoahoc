package com.example.demo.DTO;

import lombok.Data;

@Data
public class CreaterResponse {
    private String username;
    private String fullName;
    private String roleId;
    private String roleName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public CreaterResponse(String username, String fullName, String roleId, String roleName) {
        this.username = username;
        this.fullName = fullName;
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
