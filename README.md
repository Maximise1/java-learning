# Stage 2 Task 6

Добавление Swagger-документации и HATEOAS в API.

### Требования:

1. Задокументировать существующее API (из задания 4) с помощью Swagger (Springdoc OpenAPI), чтобы можно было легко изучить и тестировать API через веб-интерфейс.
2. Добавить поддержку HATEOAS, чтобы API предоставляло ссылки для навигации по ресурсам.

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
java -jar target/user-service-1.0.jar
```

6. Отправить запрос:

```
curl -X POST http://localhost:8080/users \         
  -H "Content-Type: application/json" \
  -d '{"mail":"john@mail.com","name":"John","age":30}'
```

7. Проверить, пришло ли письмо:

```
http://localhost:8025
```

8. Проверить, сгенерировал ли Swagger API:

```
http://localhost:8080/swagger-ui/index.html
```
