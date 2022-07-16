package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponseDTO {
    private long id;
    private String login;
    private String role;
}