package com.example.springboot.repository;

import com.example.springboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO:
//  T - Entity
//  ID - тип ключа, если примитив, то пишется Wrapper

// TODO:
//  1. У нас есть готовый бин - UserRepository
//  2. Здесь уже реализован CRUD (SimpleJpaRepository)
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
