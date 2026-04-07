# Stage 2 Task 7

Добавить к существующей системе паттерны: gateway api, service discovery, circuit breaker, external configuration - реализации данных паттернов можно найти в модулях spring cloud.

### Запуск проекта

1. Собрать все jar-ники

```
cd /user-service
mvn clean package
cd ../notification-service
gradle clean buid
...
```

2. Запустить проект:

```
docker compose up --build -d
```


3. Отправить запрос:

```
curl -X POST http://localhost:8080/users \         
  -H "Content-Type: application/json" \
  -d '{"mail":"john@mail.com","name":"John","age":30}'
```

4. Проверить, пришло ли письмо:

```
http://localhost:8025
```

5. Проверить, сгенерировал ли Swagger API:

```
http://localhost:8080/swagger-ui/index.html
```
