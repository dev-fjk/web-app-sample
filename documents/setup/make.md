# Make コマンド一覧

このプロジェクトで利用可能な Make コマンドの詳細説明です。

## 基本コマンド

### `make help`

利用可能なコマンドの一覧を表示します。

```bash
make help
```

## Spring Boot アプリケーションコマンド

### `make run-api`

Spring Boot アプリケーションを起動します。

```bash
make run-api
```

**実行内容**: `./gradlew bootRun`

アプリケーションは http://localhost:8080 で起動します。

### `make build`

アプリケーションをビルドします。テストも実行されます。

```bash
make build
```

**実行内容**: `./gradlew build`

ビルド成果物は `build/` ディレクトリに生成されます。

### `make clean`

ビルド成果物をクリーンアップします。

```bash
make clean
```

**実行内容**: `./gradlew clean`

`build/` ディレクトリが削除されます。

### `make test`

テストを実行します。

```bash
make test
```

**実行内容**: `./gradlew test`

テスト結果は `build/reports/tests/test/index.html` で確認できます。

## データベースコマンド

### `make db-up`

Docker Compose を使用してデータベースを起動します。

```bash
make db-up
```

**実行内容**: `docker-compose up -d`

MySQL コンテナがバックグラウンドで起動します。

**接続情報**:

- ホスト: `localhost`
- ポート: `3326`
- データベース: `web_sample_db`
- ユーザー名: `user`
- パスワード: `password`

### `make db-down`

データベースを停止します。

```bash
make db-down
```

**実行内容**: `docker-compose down`

コンテナが停止・削除されますが、データボリュームは保持されます。

### `make db-reset`

データベースをリセットします。全てのデータが削除されます。

```bash
make db-reset
```

**実行内容**: `docker-compose down -v` → `docker-compose up -d`

⚠️ **注意**: このコマンドは全てのデータベースデータを削除します。

## 開発ワークフロー

### 初回起動時

```bash
# 1. データベースを起動
make db-up

# 2. ビルド（任意）
make build

# 3. アプリケーションを起動
make run-api
```

### 通常の開発時

```bash
# データベースが起動していることを確認
docker ps

# アプリケーションを起動
make run-api
```

### クリーンビルド

```bash
# クリーンアップしてからビルド
make clean
make build
```

### データベースをリセットして再起動

```bash
# データベースをリセット
make db-reset

# アプリケーションを再起動
make run-api
```

## トラブルシューティング

### データベースに接続できない

```bash
# データベースの状態を確認
docker ps

# データベースログを確認
docker-compose logs

# データベースを再起動
make db-down
make db-up
```

### ビルドエラーが発生する

```bash
# クリーンビルドを実行
make clean
make build
```

### ポートが既に使用されている

```bash
# 既存のプロセスを確認
lsof -i :8080  # アプリケーションポート
lsof -i :3326  # データベースポート

# 必要に応じてプロセスを終了
```

## Tips

- `make` コマンドはプロジェクトルートディレクトリで実行してください
- アプリケーションの設定は `src/main/resources/application.yml` で変更できます
- ログレベルなどの設定も `application.yml` で調整可能です
