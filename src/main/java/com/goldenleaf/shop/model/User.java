package com.goldenleaf.shop.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 3)
    private String login;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate lastActivity;

    public User() {}

    public User(String login, String passwordHash, String name, LocalDate lastActivity) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.name = name;
        this.lastActivity = lastActivity;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login is empty");
        }
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
        this.login = login;
    }

    public void setPassword(String password) {
        // надо реализовать хэширование
    }

    public LocalDate getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDate lastActivity) {
        if (lastActivity == null) {
            throw new IllegalArgumentException("Last activity cannot be null");
        }
        this.lastActivity = lastActivity;
    }
}


