package ru.aston.hometask;

//Разработать консольное приложение(user-service) на Java, использующее Hibernate для взаимодействия
// с PostgreSQL, без использования Spring. Приложение должно поддерживать базовые операции CRUD
// (Create, Read, Update, Delete) над сущностью User.

//Требования:
//Использовать Hibernate в качестве ORM.
//База данных — PostgreSQL.
//Настроить Hibernate без Spring, используя или properties-файл.
//Реализовать CRUD-операции для сущности User (создание, чтение, обновление, удаление),
//которая состоит из полей: id, name, email, age, created_at.
//Использовать консольный интерфейс для взаимодействия с пользователем.
//Использовать Maven для управления зависимостями.
//Настроить логирование.
//Настроить транзакционность для операций с базой данных.
//Использовать DAO-паттерн для отделения логики работы с БД.
//Обработать возможные исключения, связанные с Hibernate и PostgreSQL

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.dao.UserDaoImpl;
import ru.aston.hometask.model.User;
import ru.aston.hometask.ui.ConsoleUi;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Создаются основные классы.");
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        UserDao userDao = new UserDaoImpl(sessionFactory);
        ConsoleUi ui = new ConsoleUi(userDao);

        logger.debug("Классы успешно созданы. Запуск основного цикла приложения.");
        ui.runApp();

        logger.debug("Завершение работы программы.");
        sessionFactory.close();
    }
}
