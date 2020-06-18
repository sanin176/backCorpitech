package com.alex.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id")
    private int id;
    private String login;
    private String passwordHash;
    private String name;
    private boolean isActive;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
