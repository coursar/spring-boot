package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// TODO:
//   0. ByteBuddy
//  +1. @Entity
//  2. No Args Constructor
//  3. private field
//  4. @Id
@Entity // требование Entity
@Table(name = "users") // только для удобства
@NoArgsConstructor // требование Entity
@AllArgsConstructor // только для удобства
@Getter
@Setter
public class UserEntity {
    @Id // требование Entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // только для удобства
    private long id;
    @Column(unique = true, nullable = false, columnDefinition = "TEXT")
    private String login;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;
}
