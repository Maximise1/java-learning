FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy jar file
COPY build/libs/locator-0.0.1-SNAPSHOT.jar app.jar

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
