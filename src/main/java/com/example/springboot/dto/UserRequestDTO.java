package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRequestDTO {
    private long id;
    private String login;
    private String password;
    private String role;
}