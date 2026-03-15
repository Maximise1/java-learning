package ru.aston.hometask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.aston.hometask.dao.UserRepository;
import ru.aston.hometask.exceptions.UserNotFoundException;
import ru.aston.hometask.model.User;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(UserDto dto) {
        User user = mapDtoToUser(dto);
        userRepository.save(user);
    }

    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return new UserDto(
                user.getEmail(),
                user.getAge(),
                user.getName(),
                user.getCreatedAt()
        );
    }

    @Transactional
    public void updateUser(UserDto dto) {
        User user = mapDtoToUser(dto);
        userRepository.save(user);
    }

    @Transactional
    private User mapDtoToUser(UserDto dto) {
        if (dto.getCreatedAt() == null) {
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
                    dto.getCreatedAt()
            );
        }
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        long deletedRows = userRepository.deleteByEmail(email);
        if (deletedRows < 1) {
            logger.warn("Failed to delete user with email: {}", email);
        }
    }
}
