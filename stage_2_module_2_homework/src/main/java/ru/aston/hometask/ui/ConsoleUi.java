package ru.aston.hometask.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.model.User;

public class ConsoleUi {

    private final UserDao userDao;
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUi.class);

    public ConsoleUi(UserDao userDao) {
        this.userDao = userDao;
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
                    try {
                        String name = args[1];
                        Integer age = Integer.parseInt(args[2]);
                        String email = args[3];
                        User newUser = new User(
                                email,
                                name,
                                age
                        );
                        userDao.create(newUser);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка при создании пользователя.");
                    }

                    break;

                case "read":
                    Long id = Long.parseLong(args[1]);
                    try {
                        User user = userDao.getById(id);
                        System.out.println("User found: id = " + user.getId()
                                + "; name = " + user.getName() + "; email = " + user.getEmail()
                                + "; age = " + user.getAge() + "; createdAt = " + user.getCreatedAt());
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка при чтении пользователя.");
                    }
                    break;

                case "update":
                    try {
                        User userToUpdate = userDao.getById(Long.parseLong(args[1]));
                        updateField(userToUpdate, args[1], args[2]);
                        userDao.update(userToUpdate);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка при изменении пользователя.");
                    }
                    break;

                case "delete":
                    try {
                        User userToDelete = userDao.getById(Long.parseLong(args[1]));
                        userDao.delete(userToDelete);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка при удалении пользователя.");
                    }
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

    private void printHelp() {
        System.out.println("Команды:");
        System.out.println("exit - выйти из приложения");
        System.out.println("help - посмотреть помощь");
        System.out.println("create <Name> <Age> <Email> - создать нового пользователя");
        System.out.println("read <id> - вывести пользователя по id");
        System.out.println("update <id> <field_to_update new_value> - обновить пользователя");
        System.out.println("delete <id> - удалить пользователя\n");
    }

    private void updateField(User userToUpdate, String field, String value) {
        switch (field) {
            case "email" -> userToUpdate.setEmail(value);
            case "name" -> userToUpdate.setName(value);
            case "age" -> userToUpdate.setAge(Integer.parseInt(value));
            default -> System.out.println("Unrecognized field: " + field);
        }
    }
}
