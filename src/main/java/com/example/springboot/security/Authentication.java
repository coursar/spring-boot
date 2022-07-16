package com.example.springboot.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Value
@Builder
public class Authentication {
    private final long id;
    private final String role;
}
