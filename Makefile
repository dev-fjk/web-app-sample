.PHONY: help build run clean test db-up db-down db-reset

# デフォルトターゲット
help:
	@echo "利用可能なコマンド:"
	@echo "  make run-api    - Spring Bootアプリケーションを起動"
	@echo "  make build      - アプリケーションをビルド"
	@echo "  make clean      - ビルド成果物をクリーンアップ"
	@echo "  make test       - テストを実行"
	@echo "  make db-up      - データベースを起動"
	@echo "  make db-down    - データベースを停止"
	@echo "  make db-reset   - データベースをリセット"

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
