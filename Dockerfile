# Build Frontend
FROM node:20-alpine AS frontend-builder

WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci

COPY frontend ./
RUN npm run build

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

# nginx のインストール
RUN apt-get update && \
    apt-get install -y nginx && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=frontend-builder /app/frontend/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/sites-available/default

# Spring Boot と nginx を起動
EXPOSE 80
CMD ["sh", "-c", "java -jar app.jar --spring.profiles.active=docker & nginx -g 'daemon off;'"]
