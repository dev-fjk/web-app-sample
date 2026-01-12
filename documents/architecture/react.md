# React フロントエンドアーキテクチャ

本ドキュメントでは、フロントエンド（React）のアーキテクチャとソース構造について説明します。

## プロジェクト概要

フロントエンドは React 18.3.1 と Vite 7.3.1 を使用して構築されています。

## 技術スタック

- **React**: 18.3.1
- **Vite**: 7.3.1
- **React Router**: 6.26.0
- **Tailwind CSS**: 3.4.13
- **ESLint**: 8.57.0

## ディレクトリ構造

```
frontend/
├── src/
│   ├── components/      # 共通コンポーネント
│   │   └── Header.jsx
│   ├── pages/           # ページコンポーネント
│   │   ├── UserList.jsx
│   │   └── UserDetail.jsx
│   ├── App.jsx          # アプリケーションのルート
│   ├── main.jsx         # エントリーポイント
│   └── index.css        # グローバルスタイル
├── index.html
├── package.json
├── vite.config.js       # Vite 設定
├── tailwind.config.js    # Tailwind CSS 設定
├── postcss.config.js     # PostCSS 設定
└── .eslintrc.cjs        # ESLint 設定
```

## コンポーネント設計

### コンポーネントの分類

#### 1. 共通コンポーネント (`src/components/`)

複数のページで再利用されるコンポーネントです。

**例**:

- `Header.jsx` - ヘッダーコンポーネント

#### 2. ページコンポーネント (`src/pages/`)

各ページに対応するコンポーネントです。

**例**:

- `UserList.jsx` - ユーザーリストページ
- `UserDetail.jsx` - ユーザー詳細ページ

### コンポーネントの設計原則

1. **単一責任の原則**: 各コンポーネントは一つの責任を持ちます
2. **再利用性**: 共通コンポーネントは再利用可能に設計します
3. **Props の型安全性**: Props の構造を明確にします

## 現在のコンポーネント

### Header

**場所**: `src/components/Header.jsx`

**役割**: アプリケーション全体のヘッダーを表示

**機能**:

- ロゴ表示
- ナビゲーションメニュー（ユーザーリスト、Swagger）

### UserList

**場所**: `src/pages/UserList.jsx`

**役割**: ユーザーリストを表示

**機能**:

- ユーザーリストの取得と表示
- 検索機能（ユーザー名、メールアドレス、電話番号）
- ページネーション
- ユーザー詳細へのリンク

### UserDetail

**場所**: `src/pages/UserDetail.jsx`

**役割**: ユーザー詳細を表示

**機能**:

- ユーザー詳細情報の取得と表示
- 戻るボタン

## ルーティング

React Router を使用してルーティングを実装しています。

```jsx
<Routes>
  <Route path="/" element={<UserList />} />
  <Route path="/users" element={<UserList />} />
  <Route path="/users/:id" element={<UserDetail />} />
</Routes>
```

## 状態管理

- ローカル状態は `useState` を使用
- グローバル状態が必要な場合は Context API を検討（現在は未使用）

## API との連携

### プロキシ設定

開発サーバーは `vite.config.js` でプロキシ設定を行っています。

```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
  },
}
```

これにより、`/api/*` へのリクエストは自動的に `http://localhost:8080` にプロキシされます。

### 使用している API エンドポイント

- `GET /api/v1/users` - ユーザーリスト取得（検索パラメータ: `userName`, `email`, `phoneNumber`, `page`, `pageSize`）
- `GET /api/v1/users/:id` - ユーザー詳細取得

## スタイリング

### Tailwind CSS

- ユーティリティクラスを優先的に使用
- カスタムスタイルが必要な場合は `index.css` に追加

### レスポンシブデザイン

Tailwind CSS のブレークポイントを使用：

- `sm:` - 640px 以上
- `md:` - 768px 以上
- `lg:` - 1024px 以上
- `xl:` - 1280px 以上

## 開発環境

### セットアップ

```bash
cd frontend
npm install
```

### 開発サーバーの起動

```bash
npm run dev
```

または、`make run` コマンドでバックエンドと同時に起動できます。

### ビルド

```bash
npm run build
```

ビルド成果物は `dist` ディレクトリに出力されます。

## コード規約

### コンポーネントの命名

- コンポーネント名は PascalCase を使用
- ファイル名はコンポーネント名と同じにする

**例**: `Header.jsx`, `UserList.jsx`

### スタイリング

- Tailwind CSS を使用
- インラインクラスでスタイルを指定
- 共通スタイルは `index.css` に定義

## パフォーマンス最適化

### メモ化

再レンダリングを防ぐために `React.memo` を使用（必要に応じて）：

```jsx
const MemoizedComponent = React.memo(Component);
```

### 遅延読み込み

大きなコンポーネントは遅延読み込みを検討（現在は未使用）：

```jsx
const LazyComponent = React.lazy(() => import("./LazyComponent"));
```
