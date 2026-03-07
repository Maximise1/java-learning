package ru.aston.hometask.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import ru.aston.hometask.dao.UserNotFoundException;
import ru.aston.hometask.service.UserDto;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.service.UserValidationException;

public class ConsoleUi {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUi.class);

    public ConsoleUi(UserService userService) {
        this.userService = userService;
    }

    public void runApp() {
        printHelp();

        appLoop : while (true) {
            String commandString = scanner.nextLine();
            String[] args = commandString.split(" ");

            switch (args[0]) {
                case "exit":
                    break appLoop;

                case "create":
                    createUser(args);
                    break;

                case "read":
                    readUser(args);
                    break;

                case "update":
                    updateUser(args);
                    break;

                case "delete":
                    deleteUser(args);
                    break;

                case "help":
                    printHelp();
                    break;

                default:
                    System.out.println("Unrecognized command: " + args[0]);
                    printHelp();
            }
        }

        logger.debug("Выход из главного цикла приложения.");
    }

    private void createUser(String[] args) {
        try {
            if (args.length < 4) {
                System.out.println("Недостаточно аргументов. Использование: create <Name> <Age> <Email>");
                return;
            }

            String name = args[1];
            Integer age = Integer.parseInt(args[2]);
            String email = args[3];

            UserDto newUser = new UserDto(email, age, name);
            userService.createUser(newUser);
            System.out.println("Пользователь успешно создан.");

        } catch (UserValidationException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат возраста.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Недостаточно аргументов.");
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка при создании пользователя.");
            logger.error("Unexpected error during user creation", e);
        }
    }

    private void readUser(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("Использование: read <id>");
                return;
            }

            Long id = Long.parseLong(args[1]);
            UserDto user = userService.getUserById(id);
            System.out.println("User found: name = " + user.getName() +
                    "; email = " + user.getMail() +
                    "; age = " + user.getAge() +
                    "; created = " + user.getCreatedAt());

        } catch (UserNotFoundException e) {
            System.out.println("Пользователь не найден.");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ID.");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении пользователя.");
            logger.error("Unexpected error during user read", e);
        }
    }

    private void updateUser(String[] args) {
        try {
            if (args.length < 4) {
                System.out.println("Использование: update <id> <field> <value>");
                return;
            }

            Long id = Long.parseLong(args[1]);
            UserDto userToUpdate = userService.getUserById(id);
            userToUpdate = updateField(userToUpdate, args[2], args[3]);
            userService.updateUser(userToUpdate);
            System.out.println("Пользователь успешно обновлен.");

        } catch (UserNotFoundException e) {
            System.out.println("Пользователь не найден.");
        } catch (UserValidationException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        } catch (InvalidFieldException e) {
            System.out.println("Некорректное поле: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат данных.");
        } catch (Exception e) {
            System.out.println("Ошибка при изменении пользователя.");
            logger.error("Unexpected error during user update", e);
        }
    }

    private void deleteUser(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("Использование: delete <id>");
                return;
            }

            Long id = Long.parseLong(args[1]);
            userService.deleteUserById(id);
            System.out.println("Пользователь успешно удален.");

        } catch (UserNotFoundException e) {
            System.out.println("Пользователь не найден.");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ID.");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя.");
            logger.error("Unexpected error during user deletion", e);
        }
    }

    private void printHelp() {
        System.out.println("Команды:");
        System.out.println("exit - выйти из приложения");
        System.out.println("help - посмотреть помощь");
        System.out.println("create <Name> <Age> <Email> - создать нового пользователя");
        System.out.println("read <id> - вывести пользователя по id");
        System.out.println("update <id> <field_to_update new_value> - обновить пользователя");
        System.out.println("delete <id> - удалить пользователя\n");
    }

    private UserDto updateField(UserDto userToUpdate, String field, String value) {
        switch (field) {
            case "email" -> {
                return new UserDto(
                        value,
                        userToUpdate.getAge(),
                        userToUpdate.getName(),
                        userToUpdate.getId(),
                        userToUpdate.getCreatedAt()
                ) ;
            }
            case "name" -> {
                return new UserDto(
                        userToUpdate.getMail(),
                        userToUpdate.getAge(),
                        value,
                        userToUpdate.getId(),
                        userToUpdate.getCreatedAt()
                );
            }
            case "age" -> {
                return new UserDto(
                        userToUpdate.getMail(),
                        Integer.parseInt(value),
                        userToUpdate.getName(),
                        userToUpdate.getId(),
                        userToUpdate.getCreatedAt()
                );
            }
            default -> throw new InvalidFieldException(field);
        }
    }
}
