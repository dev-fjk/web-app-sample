# web-app-sample

架空の BtoC グルメサイトのカスタマーサービス向けの社内アプリのサンプルアプリです。

## 利用技術

- Java: 21
- Spring Boot
- MyBatis
- Gradle
- Docker Compose
- OpenAPI / Swagger
- Make

## 環境構築

### 必要な設定・環境

- 端末: WSL or Mac
- IDE: Cursor or VSCode 推奨
- 必要なツール:
  - JDK 21
  - Docker Compose
  - Make

詳細なセットアップ手順は以下を参照してください：

- [WSL のセットアップ](./documents/setup/wsl.md)
- [MySQL のセットアップ](./documents/setup/mysql.md)

### VSCode / Cursor 拡張機能

- Formatter: josevseb.google-java-format-for-vs-code
- Spring Boot Extension Pack
- Extension Pack for Java

## クイックスタート

### 1. データベースの起動

```bash
docker compose up -d
```

### 2. アプリケーションの起動

```bash
make run-api
```

### 3. 動作確認

アプリケーション起動後、以下の URL にアクセスしてください：

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html

## ドキュメント

### 初心者向け講座

- [ウェブアプリケーション入門](./documents/lecture/webapplication.md)
- [Docker 入門](./documents/lecture/docker.md)

### セットアップガイド

- [make コマンド一覧](./documents/setup/make.md)
- [WSL セットアップ](./documents/setup/wsl.md)
- [MySQL セットアップ](./documents/setup/mysql.md)
