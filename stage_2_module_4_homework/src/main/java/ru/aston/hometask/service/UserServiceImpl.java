package ru.aston.hometask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

import jakarta.transaction.Transactional;
import ru.aston.hometask.exceptions.UserValidationException;
import ru.aston.hometask.repository.UserRepository;
import ru.aston.hometask.exceptions.UserNotFoundException;
import ru.aston.hometask.repository.model.User;
import ru.aston.hometask.service.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(UserDto dto) {
        if (userRepository.existsByEmail(dto.getMail())) {
            throw new UserValidationException("Email already in use: " + dto.getMail());
        }
        User saved = userRepository.save(mapDtoToUser(dto));
        return mapUserToDto(saved);
    }

    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return mapUserToDto(user);
    }

    @Transactional
    public UserDto updateUser(String email, UserDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!Objects.equals(user.getName(), dto.getName())) {
            user.setName(dto.getName());
        }
        if (!Objects.equals(user.getAge(), dto.getAge())) {
            user.setAge(dto.getAge());
        }

        return mapUserToDto(user);
    }

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

    private UserDto mapUserToDto(User user) {
        return new UserDto(user.getEmail(), user.getAge(), user.getName(), user.getCreatedAt());
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        if (userRepository.deleteByEmail(email) < 1) {
            throw new UserNotFoundException(email);
        }
    }
}
