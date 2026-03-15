package ru.aston.hometask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import ru.aston.hometask.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    long deleteByEmail(String email);
    boolean existsByEmail(String email);
}
