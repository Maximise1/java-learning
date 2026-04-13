# ---- Build stage ----
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy gradle files first for dependency caching
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle* .
COPY settings.gradle* .
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# Copy source and build
COPY src/ src/
RUN ./gradlew bootJar --no-daemon -x test

# ---- Run stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/notification-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
