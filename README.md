# Stage 2 Task 5

Реализовать микросервис(notification-service) для отправки сообщения на почту при удалении или добавлении пользователя.

### Требования:

1. Использовать необходимые модули spring и kafka.
2. При удалении или создании юзера приложение, реализованное до этого(user-service), должно отправлять сообщение в kafka, в котором содержится информация об операции(удаление или создание) и email юзера.
3. Новый микросервис(notification-service) должен получить сообщение из kafka и отправить сообщение на почту юзера в зависимости от операции: удаление - Здравствуйте! Ваш аккаунт был удалён. Создание - Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.
4. Также отдельно добавить API, которая будет отправлять сообщение на почту(почти тот же функционал, что и через кафку).
5. Написать интеграционные тесты для проверки отправки сообщения на почту.

### Запуск проекта

1. Запустить Mailhog

```
docker run -p 1025:1025 -p 8025:8025 mailhog/mailhog
```

2. Запустить Kafka

```
docker run -p 9092:9092 apache/kafka
```

3. Запустить контейнер c бд

```
docker run --name pg \                                                        
  -e POSTGRES_DB=mydb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=veryHardPassword \
  -p 5432:5432 \
  -d postgres
```

4. Перейти в папку с проектом notification-service, собрать jar-ник и запустить его:

```
./gradlew clean build
java -jar build/libs/notification-service.jar
```

5. Перейти в папку с проектом user-service, собрать jar-ник и запустить его:

```
mvn package
java -jar target/user-service-1.0-SNAPSHOT.jar
```

6. Отправить запрос:

```
curl -X POST <скопировать_из_консоли>/users \         
  -H "Content-Type: application/json" \
  -d '{"mail":"john@mail.com","name":"John","age":30}'
```

7. Проверить, пришло ли письмо:

```
http://localhost:8025
```
