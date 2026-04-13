# Stage 2 Task 8

Создать docker-compose.yml, который развернет всю микросервисную систему, включая Kafka, PostgreSQL, API Gateway, Service Discovery, External Configuration и 2 микросервиса(user-service и notification-service, созданные ранее). Проверить, что сервисы корректно взаимодействуют друг с другом в контейнерной среде.

### Запуск проекта

1. Запустить проект:

```
docker compose up --build -d
```


2. Отправить запрос:

```
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"mail":"john@mail.com","name":"John","age":30}'
```

3. Проверить, пришло ли письмо:

```
http://localhost:8025
```

4. Проверить, сгенерировал ли Swagger API:

```
http://localhost:8081/swagger-ui/index.html
```
