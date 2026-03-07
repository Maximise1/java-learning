package ru.aston.hometask;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.dao.UserDaoImpl;
import ru.aston.hometask.model.User;
import ru.aston.hometask.service.UserService;
import ru.aston.hometask.ui.ConsoleUi;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Создаются основные классы.");
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        UserDao userDao = new UserDaoImpl(sessionFactory);
        UserService service = new UserService(userDao);
        ConsoleUi ui = new ConsoleUi(service);

        logger.debug("Классы успешно созданы. Запуск основного цикла приложения.");
        ui.runApp();

        logger.debug("Завершение работы программы.");
        sessionFactory.close();
    }
}
