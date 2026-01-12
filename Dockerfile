# Build Jar
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle dependencies --no-daemon || true

COPY src ./src
RUN gradle clean build --no-daemon -x test

# Run Application
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8090
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]