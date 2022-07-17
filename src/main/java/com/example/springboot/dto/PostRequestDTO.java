package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequestDTO {
    private long id;
    private String content;
    private List<String> tags;
    private GeoDTO geo;
}
