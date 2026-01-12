# Docker セットアップガイド

本ドキュメントでは、Docker を使用してアプリケーションを起動する方法を説明します。

## 概要

本プロジェクトでは、Docker を使用してアプリケーションをコンテナ化して実行できます。
Docker 環境では、MySQL サーバーを立てずに、H2 Database（メモリ内データベース）を使用します。
また、nginx をリバースプロキシとして使用し、API エンドポイントを `/api/v1` で提供します。

## 前提条件

- Docker がインストールされていること

以下のコマンドで確認できます：

```bash
docker --version
```

## Docker イメージのビルド

### イメージのビルド

プロジェクトルートで以下のコマンドを実行します：

```bash
docker build -t web-app-sample .
```

ビルドには数分かかる場合があります。初回は Gradle の依存関係のダウンロードに時間がかかります。

### ビルドの確認

ビルドが成功したか確認するには：

```bash
docker images | grep web-app-sample
```

以下のような出力が表示されます：

```
web-app-sample   latest   xxxxxx   1 minute ago   500MB
```

## Docker コンテナの起動

### コンテナの起動

既存のコンテナがある場合は、先に削除してください：

```bash
docker stop web-app-sample 2>/dev/null || true
docker rm web-app-sample 2>/dev/null || true
```

以下のコマンドでコンテナを起動します：

```bash
docker run -d -p 80:80 --name web-app-sample web-app-sample
```

オプションの説明：

- `-d`: バックグラウンドで実行（デタッチモード）
- `-p 80:80`: ホストの 80 ポートをコンテナの 80 ポートにマッピング
- `--name web-app-sample`: コンテナに名前を付ける

### 起動の確認

コンテナが起動しているか確認します：

```bash
docker ps
```

以下のような出力が表示されれば起動成功です：

```
CONTAINER ID   IMAGE            COMMAND                  STATUS         PORTS                    NAMES
xxxxxxxxxxxx   web-app-sample   "sh -c java -jar..."    Up 10 seconds  0.0.0.0:80->80/tcp      web-app-sample
```

### ログの確認

アプリケーションのログを確認します：

```bash
docker logs web-app-sample
```

リアルタイムでログを確認するには：

```bash
docker logs -f web-app-sample
```

起動が完了すると、以下のようなログが表示されます：

```
Started WebApplication in X.XXX seconds
```

## 動作確認

アプリケーションが正常に起動しているか確認します：

```bash
curl http://localhost/actuator/health
```

正常な場合、以下のようなレスポンスが返ります：

```json
{ "status": "UP" }
```

ブラウザで以下の URL にアクセスできます：

- **Frontend**: http://localhost
- **Swagger UI**: http://localhost/swagger-ui.html

API エンドポイントは `/api/v1` でアクセスできます：

```bash
curl http://localhost/api/v1/users
```

## アーキテクチャ

Docker コンテナ内では以下の構成で動作します：

```
┌─────────────────────────────────┐
│         nginx (Port 80)          │
│  - 静的ファイル配信（React）     │
│  - リバースプロキシ              │
│  - セキュリティヘッダー付与      │
└─────────────────────────────────┘
              ↓
┌─────────────────────────────────┐
│   Spring Boot (Port 8090)        │
│   - H2 Database                  │
└─────────────────────────────────┘
```

### nginx のプロキシ設定

- `/` → フロントエンド（React）の静的ファイル
- `/api/*` → Spring Boot (`localhost:8090`)
- `/swagger-ui.html`, `/swagger-ui/*` → Spring Boot
- `/v3/api-docs` → Spring Boot
- `/actuator/*` → Spring Boot

## コンテナの管理

### コンテナの停止

```bash
docker stop web-app-sample
```

### コンテナの再起動

```bash
docker start web-app-sample
```

### コンテナの削除

コンテナを停止してから削除します：

```bash
docker stop web-app-sample
docker rm web-app-sample
```

### イメージの削除

コンテナを削除した後、イメージも削除できます：

```bash
docker rmi web-app-sample
```

## トラブルシューティング

### ポートが既に使用されている場合

エラーメッセージ：

```
Error response from daemon: Ports are not available: exposing port TCP 0.0.0.0:80->80/tcp: bind: address already in use
```

**解決方法**：

1. 別のポートを使用する：

   ```bash
   docker run -d -p 8080:80 --name web-app-sample web-app-sample
   ```

   この場合、`http://localhost:8080`でアクセスします。

2. 既存のコンテナを停止・削除する：

   ```bash
   docker stop web-app-sample
   docker rm web-app-sample
   ```

### コンテナがすぐに停止する場合

コンテナが起動直後に停止する場合、ログを確認します：

```bash
docker logs web-app-sample
```

エラーメッセージを確認し、原因を特定します。

### ビルドが失敗する場合

**依存関係のダウンロードエラー**：

- インターネット接続を確認
- Docker のネットワーク設定を確認

**メモリ不足エラー**：

- Docker Desktop のメモリ設定を増やす（推奨: 4GB 以上）

### nginx が起動しない場合

nginx の設定を確認します：

```bash
docker exec web-app-sample nginx -t
```

設定ファイルの構文エラーがないか確認できます。

## 関連ドキュメント

- [make コマンド一覧](./make.md)
- [MySQL セットアップ](./mysql.md)
- [Spring Boot アーキテクチャ](../architecture/springboot.md)
- [React フロントエンドアーキテクチャ](../architecture/react.md)
