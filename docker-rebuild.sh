#!/bin/bash

set -e

CONTAINER_NAME="web-app-sample"
IMAGE_NAME="web-app-sample"
PORT="80"

echo "Stopping container if running..."
docker stop ${CONTAINER_NAME} 2>/dev/null || true

echo "Removing container..."
docker rm ${CONTAINER_NAME} 2>/dev/null || true

echo "Building Docker image..."
docker build -t ${IMAGE_NAME} .

echo "Starting container..."
docker run -d -p ${PORT}:80 --name ${CONTAINER_NAME} ${IMAGE_NAME}

echo "Waiting for application to start..."
sleep 5

echo "Checking health..."
if curl -s http://localhost/actuator/health > /dev/null; then
  echo "✅ Application is running!"
  echo "Frontend: http://localhost"
  echo "Swagger UI: http://localhost/swagger-ui.html"
else
  echo "⚠️  Health check failed. Check logs with: docker logs ${CONTAINER_NAME}"
fi
