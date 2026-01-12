.PHONY: help build run clean test db-up db-down db-reset run-fe stop docker-rebuild

# デフォルトターゲット
help:
	@echo "利用可能なコマンド:"
	@echo "  make run        - Spring BootとReactを同時に起動"
	@echo "  make stop       - 起動中のアプリケーションを停止"
	@echo "  make run-fe     - Reactアプリケーションを起動"
	@echo "  make install    - npm install"
	@echo "  make run-api    - Spring Bootアプリケーションを起動"
	@echo "  make build      - アプリケーションをビルド"
	@echo "  make clean      - ビルド成果物をクリーンアップ"
	@echo "  make test       - テストを実行"
	@echo "  make db-up      - データベースを起動"
	@echo "  make db-down    - データベースを停止"
	@echo "  make db-reset   - データベースをリセット"
	@echo "  make docker-rebuild - Dockerコンテナを停止・削除・ビルド・起動"

# All Commands
## Spring BootとReactを同時に起動
run:
	@echo "Spring Boot and React are starting..."
	@echo "Press Ctrl+C to stop"
	@bash -c ' \
	trap "pkill -f gradlew || true; pkill -f vite || true" EXIT INT TERM; \
	./gradlew bootRun & \
	echo "Waiting for Spring Boot to start..."; \
	for i in $$(seq 1 60); do \
		if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then \
			echo "Spring Boot is ready!"; \
			break; \
		fi; \
		sleep 1; \
	done; \
	(cd frontend && npm run dev) & \
	wait'

## 起動中のアプリケーションを停止
stop:
	@pkill -f "gradlew bootRun" || true
	@pkill -f "vite" || true
	@echo "アプリケーションを停止しました"

# Frontend Commands
## Reactアプリケーションの起動
run-fe:
	cd frontend && npm run dev

install:
	cd frontend && npm install

# Spring boot Comamands
# Spring Bootアプリケーションの起動
run-api:
	./gradlew bootRun

# アプリケーションのビルド
build:
	./gradlew build

# ビルド成果物のクリーンアップ
clean:
	./gradlew clean

# テストの実行
test:
	./gradlew test

# DB Commands
## データベースの起動
db-up:
	docker-compose up -d

## データベースの停止
db-down:
	docker-compose down

## データベースのリセット
db-reset:
	docker-compose down -v
	docker-compose up -d

# Docker Commands
## Dockerコンテナを停止・削除・ビルド・起動
docker-rebuild:
	@chmod +x docker-rebuild.sh
	@./docker-rebuild.sh
