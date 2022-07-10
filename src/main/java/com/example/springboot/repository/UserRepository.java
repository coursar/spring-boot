package com.example.springboot.repository;

import com.example.springboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// TODO:
//  T - Entity
//  ID - тип ключа, если примитив, то пишется Wrapper

// TODO:
//  1. У нас есть готовый бин - UserRepository
//  2. Здесь уже реализован CRUD (SimpleJpaRepository)
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // TODO: JPA:
    //  1. ReadOnly
    @Query("SELECT e FROM UserEntity e WHERE e.login = :login")
    Optional<UserEntity> findByLogin(String login);

    // TODO: JPA:
    //  2. Modifying
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity e SET e.login = :login, e.password = :password WHERE e.id = :id")
    void update(long id, String login, String password);
}
