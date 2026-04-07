FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy jar file
COPY build/libs/notification-service.jar app.jar

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
