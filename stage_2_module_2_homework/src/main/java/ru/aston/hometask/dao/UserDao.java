package ru.aston.hometask.dao;

import ru.aston.hometask.model.User;

public interface UserDao {
    void create(User user);
    User getById(Long id);
    void update(User user);
    void deleteById(Long id);
}
