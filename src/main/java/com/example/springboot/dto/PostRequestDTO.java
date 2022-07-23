package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequestDTO {
    @Min(0)
    private long id;
    @NotBlank
    private String content;
    @Size(min = 1)
//    private List<@NotNull @Pattern(regexp = "#[A-Za-z0-9А-Яа-яЁё]{1,100}") String> tags;
    private List<@NotNull @Pattern(regexp = "#[A-Za-z0-9\\u0410-\\u042F\\u0430-\\u044F\\u0401\\u0451]{1,100}") String> tags;
    private @Valid GeoDTO geo;
}
