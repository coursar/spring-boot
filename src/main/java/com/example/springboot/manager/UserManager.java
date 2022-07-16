package com.example.springboot.manager;

import com.example.springboot.entity.UserEntity;
import com.example.springboot.exception.ForbiddenException;
import com.example.springboot.exception.UserLoginNotFoundException;
import com.example.springboot.exception.UserNotFoundException;
import com.example.springboot.exception.UserPasswordNotMatchesException;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.security.Authentication;
import com.example.springboot.security.Roles;
import lombok.RequiredArgsConstructor;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository; // мне через DI подставят нужный интерфейс
    private final PasswordEncoder passwordEncoder;
    private final Function<UserEntity, UserResponseDTO> userEntityToUserResponseDTO = userEntity -> new UserResponseDTO(
            userEntity.getId(),
            userEntity.getLogin(),
            userEntity.getRoles()
    );

    public List<UserResponseDTO> getAll(final Authentication authentication) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException(); // 403 @ResponseStatus
        }

         return userRepository.findAll().stream()
                 .map(userEntityToUserResponseDTO)
                 .collect(Collectors.toList())
                 ;
    }

    public UserResponseDTO getById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException(); // 403 @ResponseStatus
        }

        return userRepository.findById(id)
                // map срабатывает только тогда, когда внутри есть объект
                .map(userEntityToUserResponseDTO)
                .orElseThrow(UserNotFoundException::new) // () -> new UserNotFoundException <-> UserNotFoundException::new
                ;
    }

    public UserResponseDTO create(final Authentication authentication, final UserRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException(); // 403 @ResponseStatus
        }
        // TODO: check login

        // TODO:
        //  +1. Создаём entity на базе DTO
        //  +2. Вызываем save
        //  3. Превращаем entity в DTO
        final UserEntity userEntity = new UserEntity(
                0,
                requestDTO.getLogin(),
                passwordEncoder.encode(requestDTO.getPassword()),
                requestDTO.getRoles()
        );
        final UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityToUserResponseDTO.apply(savedEntity);
    }

    public UserResponseDTO update(final Authentication authentication, final UserRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException(); // 403 @ResponseStatus
        }

        // TODO:
        //  1. JPA нет UPDATE -> getReferenceById + setPassword/setLogin
        //  2. JPQL
        final UserEntity userEntity = userRepository.getReferenceById(requestDTO.getId());
        userEntity.setLogin(requestDTO.getLogin());
        userEntity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        // TODO: после изменения create всегда смотрите на update
        userEntity.setRoles(requestDTO.getRoles());

        return userEntityToUserResponseDTO.apply(userEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException(); // 403 @ResponseStatus
        }

        userRepository.deleteById(id);
    }

    public Authentication authenticateByLoginAndPassword(
            final String login,
            final String password
    ) {
        // TODO:
        //  +1. Вытащить пользователя по логину
        //  +2. Сравнить через passwordEncoder пароли
        //  3. Если всё ок, то собираем Authentication
        final UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(UserLoginNotFoundException::new);
//                .orElseThrow(new Supplier<RuntimeException>() {
//                    @Override
//                    public RuntimeException get() {
//                        return new UserLoginNotFoundException();
//                    }
//                });

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new UserPasswordNotMatchesException();
        }

        // pattern builder:
        //  1. .builder()
        //  2. "выставление полей"
        //  3. build()
        final Authentication authentication = Authentication.builder()
                .id(userEntity.getId())
                .roles(new ArrayList<>(userEntity.getRoles()))
                .build();

        return authentication;
    }
}
