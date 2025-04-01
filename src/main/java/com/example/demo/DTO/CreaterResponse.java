package com.example.demo.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreaterResponse {
    private String username;
    private String fullName;
    private String roleId;
    private String roleName;
}
