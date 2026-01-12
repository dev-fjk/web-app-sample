# web-app-sample

架空の BtoC グルメサイトのカスタマーサービス向けの社内アプリのサンプルアプリです。

## 動作確認(Swagger)

Swagger を用意しているため、アプリケーション実行後、以下の URL の Swagger から確認してください。

http://localhost:8080/swagger-ui/index.html

## 利用ツール

- Java: 21
- React
- MyBatis
- Gradle
- Docker Compose
- Open API
- Make

## 必要な設定・環境

- 端末
  - WSL or Mac
  - WSL を使って作成しています。
- IDE
  - Cursor or VSCode 推奨
  - 開発は Cursor を利用して行っています。
- 導入必要なツールなど
  - JDK21
  - Docker Compose
    - MySQL 建てるだけなので、Docker 無しで MySQL を自前で用意しても OK です
  - Make (WSL では通常インストール済み、Mac では `xcode-select --install` でインストール)

## VSCode 拡張機能

- Formatter: josevseb.google-java-format-for-vs-code
- Spring Boot Extension Pack
- Extension Pack for Java

## アプリケーションの実行

このプロジェクトでは Makefile を使用してアプリケーションを実行できます。

### Makefile の使い方

利用可能なコマンドを確認：

```bash
make help
```

### 基本的なコマンド

- **アプリケーションのビルド**

  ```bash
  make build
  ```

- **Spring Boot アプリケーションの起動**

  ```bash
  make run
  ```

- **ビルド成果物のクリーンアップ**

  ```bash
  make clean
  ```

- **テストの実行**
  ```bash
  make test
  ```

### データベース関連のコマンド

- **データベースの起動**

  ```bash
  make db-up
  ```

- **データベースの停止**

  ```bash
  make db-down
  ```

- **データベースのリセット**
  ```bash
  make db-reset
  ```

### 開発の流れ

1. データベースを起動

   ```bash
   make db-up
   ```

2. アプリケーションを起動

   ```bash
   make run
   ```

3. ブラウザで Swagger UI を開く
   - http://localhost:8080/swagger-ui/index.html
