# Stage 2 Task 4

Добавить в user-service поддержку Spring и разработать API, которое позволит управлять данными.

### Требования:

1. Использовать необходимые модули spring(boot, web, data etc).
2. Реализовать api для получения, создания, обновления и удаления юзера. Важно, entity не должен возвращаться из контроллера, необходимо использовать dto.
3. Заменить Hibernate на Spring data JPA.
4. Написать тесты для API(можно делать это при помощи mockMvc или других средств)

### Запуск проекта

1. Установить Tomcat

```
sudo apt update
sudo apt install tomcat10
sudo systemctl start tomcat10
```

2. Проверить установку

```
http://localhost:8080
```

3. Запустить контейнер

```
docker run --name pg \                                                  
  -e POSTGRES_DB=mydb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=veryHardPassword \
  -p 5432:5432 \
  -d postgres
```

4. Создать таблицу

```
docker exec -it <container_id> psql -U postgres -d mydb
CREATE TABLE users (
    id        BIGSERIAL PRIMARY KEY,
    email     VARCHAR(255) NOT NULL,
    name      VARCHAR(50)  NOT NULL,
    age       INTEGER,
    createdat TIMESTAMP
);
CREATE TABLE
```

5. Скопировать .war приложения в нужную директорию

```
sudo cp build/libs/your-project.war /var/lib/tomcat10/webapps/
```

6. Проверить работу программы

```
curl http://localhost:8080/Stage_2_module_4_homework/users/test@mail.com 
```
