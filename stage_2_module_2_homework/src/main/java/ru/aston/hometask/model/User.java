package ru.aston.hometask.model;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.NaturalId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    @NaturalId
    @Column(nullable = false)
    private String email;

    @Min(0)
    @Max(150)
    private Integer age;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false)
    @Nationalized
    private String name;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected User() {}

    public User(String email, String name, Integer age, LocalDateTime createdAt) {
        this.age = age;
        this.createdAt = createdAt;
        this.email = email;
        this.name = name;
    }

    public User(String email, String name, Integer age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
