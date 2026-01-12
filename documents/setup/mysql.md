### データベース接続情報

- ホスト: `localhost`
- ポート: `3326`
- データベース名: `web_sample_db`
- ユーザー名: `user`
- パスワード: `password`
- ルートパスワード: `rootpassword`

## データベースセットアップ

このプロジェクトでは Docker Compose を使用して MySQL データベースを起動します。

### データベースの起動

```bash
docker-compose up -d
```

データベースコンテナが起動し、自動的にテーブル作成と初期データ投入が実行されます。

### データベースの停止

```bash
docker-compose down
```

### データベースの完全リセット（ボリュームも削除）

データベースを初期状態に戻す場合：

```bash
docker-compose down -v
docker-compose up -d
```

### データベースの状態確認

コンテナの状態を確認：

```bash
docker-compose ps
```

ログを確認：

```bash
docker-compose logs db
```

### データベースへの接続（MySQL クライアントから）

```bash
mysql -h localhost -P 3326 -u user -ppassword web_sample_db
```
