# web-app-sample

架空の BtoC グルメサイトのカスタマーサービス向けの社内アプリのサンプルアプリです。

## 利用技術

### バックエンド

- Java: 21
- Spring Boot
- MyBatis
- Gradle
- Docker Compose
- OpenAPI / Swagger
- Make

### フロントエンド

- React: 18.3.1
- Vite: 7.3.1
- React Router: 6.26.0
- Tailwind CSS: 3.4.13
- ESLint: 8.57.0

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
- [Docker セットアップ](./documents/setup/docker.md)
- [フロントエンドセットアップ](./documents/setup/frontend.md)

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

#### バックエンドとフロントエンドを同時に起動

```bash
make run
```

**注意**: フロントエンドを起動する前に、`frontend` ディレクトリで `npm install` を実行してください。

#### バックエンドのみ起動

```bash
make run-api
```

#### フロントエンドのみ起動

```bash
make run-fe
```

### 3. 動作確認

アプリケーション起動後、以下の URL にアクセスしてください：

- **Frontend**: http://localhost:3000
- **Swagger UI**: http://localhost:3000/swagger-ui.html（開発環境）または http://localhost/swagger-ui.html（Docker環境）

## ドキュメント

### アーキテクチャ

ソースコードの詳細は以下ディレクトリを参照してください。

- [Spring Boot アーキテクチャ](./documents/architecture/springboot.md)
- [React フロントエンドアーキテクチャ](./documents/architecture/react.md)

### セットアップガイド

- [フロントエンドセットアップ](./documents/setup/frontend.md)
- [make コマンド一覧](./documents/setup/make.md)
- [WSL セットアップ](./documents/setup/wsl.md)
- [MySQL セットアップ](./documents/setup/mysql.md)
- [Docker セットアップ](./documents/setup/docker.md)

### 初心者向け講座

- [ウェブアプリケーション入門](./documents/lecture/webapplication.md)
- [Docker 入門](./documents/lecture/docker.md)
- [CI/CD 入門](./documents/lecture/cicd.md)
- [ユニットテスト入門](./documents/lecture/unittest.md)
