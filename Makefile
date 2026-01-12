.PHONY: help build run clean test db-up db-down db-reset

# デフォルトターゲット
help:
	@echo "利用可能なコマンド:"
	@echo "  make build      - アプリケーションをビルド"
	@echo "  make run        - Spring Bootアプリケーションを起動"
	@echo "  make clean      - ビルド成果物をクリーンアップ"
	@echo "  make test       - テストを実行"
	@echo "  make db-up      - データベースを起動"
	@echo "  make db-down    - データベースを停止"
	@echo "  make db-reset   - データベースをリセット"

# アプリケーションのビルド
build:
	./gradlew build

# Spring Bootアプリケーションの起動
run:
	./gradlew bootRun

# ビルド成果物のクリーンアップ
clean:
	./gradlew clean

# テストの実行
test:
	./gradlew test

# データベースの起動
db-up:
	docker-compose up -d

# データベースの停止
db-down:
	docker-compose down

# データベースのリセット
db-reset:
	docker-compose down -v
	docker-compose up -d
