# フロントエンドセットアップガイド

本ドキュメントでは、フロントエンド（React）のセットアップ手順を説明します。

## 前提条件

- Node.js 18 以上がインストールされていること
- npm または yarn がインストールされていること

以下のコマンドで確認できます：

```bash
node --version
npm --version
```

## Node.js のインストール

### WSL / Linux の場合

Node.js の公式リポジトリからインストールする方法：

```bash
# NodeSource のリポジトリを追加
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -

# Node.js をインストール
sudo apt-get install -y nodejs
```

または、nvm（Node Version Manager）を使用する方法：

```bash
# nvm をインストール
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash

# ターミナルを再起動後、Node.js をインストール
nvm install 20
nvm use 20
```

### Mac の場合

Homebrew を使用：

```bash
brew install node
```

または、公式インストーラーからダウンロード：
https://nodejs.org/

## 依存関係のインストール

プロジェクトルートで以下のコマンドを実行します：

```bash
make install
```

または、直接実行する場合：

```bash
cd frontend
npm install
```

インストールには数分かかる場合があります。初回は依存関係のダウンロードに時間がかかります。

### インストールの確認

`frontend/node_modules` ディレクトリが作成されていれば成功です：

```bash
ls frontend/node_modules
```

## 開発サーバーの起動

### フロントエンドのみ起動

```bash
make run-fe
```

または、直接実行する場合：

```bash
cd frontend
npm run dev
```

開発サーバーが起動すると、以下の URL でアクセスできます：

- **Frontend**: http://localhost:3000

### バックエンドと同時に起動

```bash
make run
```

このコマンドで、Spring Boot と React の両方が同時に起動します。

## 動作確認

ブラウザで以下の URL にアクセスして、フロントエンドが正常に動作しているか確認します：

```
http://localhost:3000
```

ユーザーリストが表示されれば成功です。

## ビルド

本番用のビルドを作成します：

```bash
cd frontend
npm run build
```

ビルド成果物は `frontend/dist` ディレクトリに出力されます。

### ビルドのプレビュー

ビルドしたアプリケーションをプレビューします：

```bash
cd frontend
npm run preview
```

## Lint

コードの静的解析を実行します：

```bash
cd frontend
npm run lint
```

自動修正：

```bash
cd frontend
npm run lint:fix
```

## トラブルシューティング

### Node.js が見つからない

エラーメッセージ：

```
bash: node: command not found
```

**解決方法**：

1. Node.js がインストールされているか確認：

   ```bash
   which node
   ```

2. パスが通っているか確認：

   ```bash
   echo $PATH
   ```

3. nvm を使用している場合、ターミナルを再起動するか、以下を実行：

   ```bash
   source ~/.bashrc
   ```

### npm install が失敗する場合

**ネットワークエラー**：

- インターネット接続を確認
- プロキシ設定を確認（企業ネットワークの場合）

**権限エラー**：

```bash
sudo chown -R $(whoami) ~/.npm
```

**キャッシュのクリア**：

```bash
npm cache clean --force
rm -rf frontend/node_modules frontend/package-lock.json
cd frontend
npm install
```

### ポート 3000 が既に使用されている場合

エラーメッセージ：

```
Error: listen EADDRINUSE: address already in use :::3000
```

**解決方法**：

1. 既存のプロセスを確認：

   ```bash
   lsof -i :3000
   ```

2. プロセスを終了：

   ```bash
   kill -9 <PID>
   ```

3. または、別のポートを使用（`vite.config.js` を編集）：

   ```javascript
   server: {
     port: 3001,
   }
   ```

### プロキシが動作しない

フロントエンドから API にアクセスできない場合：

1. バックエンドが起動しているか確認：

   ```bash
   curl http://localhost:8080/actuator/health
   ```

2. `vite.config.js` のプロキシ設定を確認：

   ```javascript
   proxy: {
     '/api': {
       target: 'http://localhost:8080',
       changeOrigin: true,
     },
   }
   ```

3. ブラウザの開発者ツールでネットワークタブを確認し、リクエストが正しく送信されているか確認

### スタイルが適用されない

Tailwind CSS のスタイルが適用されない場合：

1. `tailwind.config.js` の `content` パスを確認：

   ```javascript
   content: [
     "./index.html",
     "./src/**/*.{js,ts,jsx,tsx}",
   ]
   ```

2. `src/index.css` に Tailwind のディレクティブが含まれているか確認：

   ```css
   @tailwind base;
   @tailwind components;
   @tailwind utilities;
   ```

3. 開発サーバーを再起動

## 関連ドキュメント

- [make コマンド一覧](./make.md)
- [React フロントエンドアーキテクチャ](../architecture/react.md)
- [Spring Boot アーキテクチャ](../architecture/springboot.md)
