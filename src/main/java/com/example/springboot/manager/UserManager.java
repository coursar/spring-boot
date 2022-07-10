package com.example.springboot.manager;

import lombok.RequiredArgsConstructor;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.exception.UserLoginAlreadyRegisteredException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final RowMapper<UserResponseDTO> rowMapper = (rs, rowNum) ->
            new UserResponseDTO(rs.getLong("id"), rs.getString("login"));

    public List<UserResponseDTO> getAll() {
        return jdbcOperations.query(
                // language=PostgreSQL
                """
                        SELECT id, login FROM users
                        """,
                rowMapper
        );
    }

    public UserResponseDTO getById(final long id) {
        return jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        SELECT id, login FROM users WHERE id = :id
                        """,
                Map.of("id", id),
                rowMapper
        );
    }

    public UserResponseDTO create(final UserRequestDTO requestDTO) {
        final boolean loginAlreadyRegistered = jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        SELECT count(*) != 0 FROM users WHERE login = :login
                        """,
                Map.of("login", requestDTO.getLogin()),
                Boolean.class
        );
        if (loginAlreadyRegistered) {
            throw new UserLoginAlreadyRegisteredException(requestDTO.getLogin());
        }

        return jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO users(login, password) VALUES (:login, :password) RETURNING id, login
                        """,
                Map.of(
                        "login", requestDTO.getLogin(),
                        "password", requestDTO.getPassword()
                ),
                rowMapper
        );
    }

    public UserResponseDTO update(final UserRequestDTO requestDTO) {
        final boolean loginAlreadyRegistered = jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        SELECT count(*) != 0 FROM users WHERE id != :id AND login = :login
                        """,
                Map.of(
                        "id", requestDTO.getId(),
                        "login", requestDTO.getLogin()
                ),
                Boolean.class
        );
        if (loginAlreadyRegistered) {
            throw new UserLoginAlreadyRegisteredException(requestDTO.getLogin());
        }

        return jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        UPDATE users SET login = :login, password = :password WHERE id = :id RETURNING id, login
                        """,
                Map.of(
                        "id", requestDTO.getId(),
                        "login", requestDTO.getLogin(),
                        "password", requestDTO.getPassword()
                ),
                rowMapper
        );
    }

    public void deleteById(final long id) {
        jdbcOperations.update(
                // language=PostgreSQL
                """
                        DELETE FROM users WHERE id = :id
                        """,
                Map.of("id", id)
        );
    }
}
