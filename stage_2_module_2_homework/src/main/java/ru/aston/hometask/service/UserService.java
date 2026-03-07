package ru.aston.hometask.service;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.model.User;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(UserDto dto) {
        User user = mapDtoToUser(dto);
        userDao.create(user);
    }

    public UserDto getUserById(Long id) {
        User user = userDao.getById(id);
        return new UserDto(
                user.getEmail(),
                user.getAge(),
                user.getName(),
                id,
                user.getCreatedAt()
        );
    }

    public void updateUser(UserDto dto) {
        User user = mapDtoToUser(dto);
        userDao.update(user);
    }

    private User mapDtoToUser(UserDto dto) {
        if (dto.getId() == null && dto.getCreatedAt() == null) {
            return new User(
                    dto.getMail(),
                    dto.getName(),
                    dto.getAge()
            );
        } else {
            return new User(
                    dto.getMail(),
                    dto.getName(),
                    dto.getAge(),
                    dto.getId(),
                    dto.getCreatedAt()
            );
        }
    }

    public void deleteUserById(Long id) {
        userDao.deleteById(id);
    }
}
