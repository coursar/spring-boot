package com.example.springboot.controller;

import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.manager.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // генерирует конструктор только для final non-static полей
public class UserController {
    private final UserManager manager;

    @GetMapping("/users")
    public List<UserResponseDTO> getAll() {
        final List<UserResponseDTO> responseDTO = manager.getAll();
        return responseDTO;
    }

    // TODO: http://localhost:8080/users/1
    @GetMapping("/users/{id}")
    public UserResponseDTO getById(@PathVariable final long id) {
        final UserResponseDTO responseDTO = manager.getById(id);
        return responseDTO;
    }

    @PostMapping("/users")
    public UserResponseDTO create(@RequestBody final UserRequestDTO requestDTO) {
        final UserResponseDTO responseDTO = manager.create(requestDTO);
        return responseDTO;
    }

    @PutMapping("/users")
    public UserResponseDTO update(@RequestBody final UserRequestDTO requestDTO) {
        final UserResponseDTO responseDTO = manager.update(requestDTO);
        return responseDTO;
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable final long id) {
        manager.deleteById(id);
    }
}
