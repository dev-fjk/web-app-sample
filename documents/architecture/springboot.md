# Spring Boot

本ドキュメントでは、web-app-sample プロジェクトの Spring Boot アーキテクチャとソース構造について説明します。

## プロジェクト概要

本プロジェクトは、架空の BtoC グルメサイトのカスタマーサービス向けの社内アプリのサンプルアプリケーションです。
Spring Boot 3.5.9 と Java 21 を使用した RESTful API アプリケーションです。

## アーキテクチャ概要

本プロジェクトは、**レイヤードアーキテクチャ**を採用しています。以下のレイヤーで構成されています：

```
┌─────────────────────────────────────┐
│      Presentation Layer            │
│  (Controller, Filter, AOP)         │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│      Application Layer              │
│  (Service)                          │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│      Domain Layer                   │
│  (Repository, Domain Models)        │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│      Infrastructure Layer           │
│  (Mapper, Entity, DTO)              │
└─────────────────────────────────────┘
```

## パッケージ構造

```

fjk.app.web.sample/
├── WebApplication.java              # アプリケーションエントリーポイント
│
├── controller/                      # プレゼンテーション層
│   └── UserController.java          # ユーザーAPIコントローラー
│
├── service/                         # アプリケーション層
│   └── UserService.java             # ユーザーサービス（ビジネスロジック）
│
├── repository/                      # ドメイン層
│   └── UserRepository.java          # ユーザーリポジトリ
│
├── mapper/                          # インフラストラクチャ層
│   └── UserMapper.java              # MyBatisマッパーインターフェース
│
├── models/                          # モデルクラス
│   ├── presentation/                # プレゼンテーション層用モデル
│   │   ├── query/                   # リクエストクエリ
│   │   │   └── UserListQuery.java
│   │   └── response/                # レスポンス
│   │       ├── users/
│   │       │   ├── UserResponse.java
│   │       │   └── UserListResponse.java
│   │       ├── common/
│   │       │   └── Pagination.java
│   │       └── errors/
│   │           ├── ApiErrorResponse.java
│   │           └── ValidaitonErrorResponse.java
│   ├── domain/                      # ドメイン層用モデル
│   │   ├── dto/
│   │   │   └── PagingDto.java
│   │   └── result/
│   │       └── UserListResult.java
│   └── infra/                       # インフラストラクチャ層用モデル
│       ├── entity/
│       │   └── User.java            # エンティティ
│       └── dto/
│           └── UserDbSearchDto.java # DB検索用DTO
│
├── filter/                          # フィルター
│   └── ApiFilter.java               # リクエストID生成・アクセスログ出力
│
├── aop/                             # AOP
│   └── LoggingAspect.java           # コントローラーのリクエスト/レスポンスログ出力
│
├── exceptions/                      # 例外処理
│   ├── GlobalExceptionHandler.java  # グローバル例外ハンドラー
│   └── custom/
│       ├── ResourceNotFoundException.java
│       └── CustomValidationException.java
│
├── components/                      # 共通コンポーネント
│   └── BeanValidationErrorThrower.java
│
└── config/                          # 設定クラス
    ├── ApplicationConfig.java       # アプリケーション設定
    ├── OpenApiConfig.java           # OpenAPI/Swagger設定
    └── StartupLogger.java           # 起動時ログ出力
```

## 各レイヤーの詳細

### Presentation Layer（プレゼンテーション層）

HTTP リクエスト/レスポンスを処理する層です。

#### Controller

- **UserController**: ユーザー関連の API エンドポイントを提供
  - `GET /v1/users/{id}`: ユーザー取得
  - `GET /v1/users`: ユーザー一覧検索

#### Filter

- **ApiFilter**:
  - リクエストごとにユニークな ID を生成し、MDC に設定
  - レスポンス時にアクセスログを出力

#### AOP

- **LoggingAspect**:
  - Controller のリクエスト/レスポンス内容をログ出力
  - レスポンスタイムを計測・出力
  - GET リクエストの場合は引数をログ出力しない

### Application Layer（アプリケーション層）

ビジネスロジックを実装する層です。

#### Service

- **UserService**:
  - ユーザー取得処理
  - ユーザー一覧検索処理（ページング対応）
  - ドメインロジックの調整

### Domain Layer（ドメイン層）

ドメインロジックとデータアクセスの抽象化を提供する層です。

#### Repository

- **UserRepository**:
  - ユーザー検索のロジックを実装
  - ページング処理の調整

### Infrastructure Layer（インフラストラクチャ層）

データベースアクセスなどの技術的な実装を提供する層です。

#### Mapper

- **UserMapper**: MyBatis マッパーインターフェース
  - SQL クエリの定義
  - データベースアクセス

## データフロー

### ユーザー取得 API の例

```
1. HTTP Request
   ↓
2. ApiFilter (リクエストID生成、MDC設定)
   ↓
3. LoggingAspect (リクエストログ出力)
   ↓
4. UserController.find()
   ↓
5. UserService.findById()
   ↓
6. UserMapper.findById() (MyBatis)
   ↓
7. Database (MySQL/H2)
   ↓
8. UserResponse 生成
   ↓
9. LoggingAspect (レスポンスログ出力)
   ↓
10. ApiFilter (アクセスログ出力、MDCクリア)
   ↓
11. HTTP Response
```

### ユーザー一覧検索 API の例

```
1. HTTP Request (クエリパラメータ)
   ↓
2. ApiFilter
   ↓
3. LoggingAspect
   ↓
4. UserController.search()
   ↓
5. BeanValidationErrorThrower (バリデーション)
   ↓
6. UserService.searchUsers()
   ↓
7. UserRepository.search()
   ↓
8. UserMapper.count() + UserMapper.findAll()
   ↓
9. Database
   ↓
10. UserListResponse 生成
   ↓
11. LoggingAspect
   ↓
12. ApiFilter
   ↓
13. HTTP Response
```

## 例外処理

### GlobalExceptionHandler

- アプリケーション全体の例外をキャッチして適切な HTTP レスポンスに変換
- `ResourceNotFoundException` → 404 Not Found
- `CustomValidationException` → 400 Bad Request

## ログ出力

### ログの種類

1. **アクセスログ** (ApiFilter)

   - HTTP メソッド、URI、ステータスコード、レスポンスタイム、リモートアドレス

2. **リクエストログ** (LoggingAspect)

   - HTTP メソッド、URI、コントローラー名、メソッド名、引数（GET 以外）

3. **レスポンスログ** (LoggingAspect)
   - コントローラー名、メソッド名、レスポンス内容、レスポンスタイム

### MDC（Mapped Diagnostic Context）

- リクエスト ID を MDC に設定し、すべてのログに含まれる
- JSON ログ形式で出力（logback-spring.xml で設定）

## データベース

### 開発環境

- **MySQL 8.0**: docker-compose で起動
- ポート: 3326

### Docker 環境

- **H2 Database**: メモリ内データベース
- プロファイル: `docker`
- ポート: 8090

### 初期化

- `mysql/01_schema.sql`: スキーマ定義
- `mysql/02_data.sql`: 初期データ
- `src/main/resources/h2/schema.sql`: H2 用スキーマ
- `src/main/resources/h2/data.sql`: H2 用初期データ

## テスト

### テスト構造

```
src/test/java/fjk/app/web/sample/
├── controller/
│   └── UserControllerTest.java      # Controllerのテスト（@WebMvcTest）
├── service/
│   └── UserServiceTest.java         # Serviceのテスト（@ExtendWith(MockitoExtension.class)）
├── repository/
│   └── UserRepositoryTest.java      # Repositoryのテスト
└── components/
    └── BeanValidationErrorThrowerTest.java
```

### テスト方針

- **Controller**: `@WebMvcTest`と`@MockitoBean`を使用
- **Service/Repository**: `@Mock`と`@InjectMocks`を使用した純粋なユニットテスト
- モックを使用して依存関係を分離

## 設定ファイル

### application.yml

- デフォルト設定（MySQL 接続情報は環境変数から取得）

### application-local.yml

- ローカル開発環境用設定

### application-docker.yml

- Docker 環境用設定（H2 Database 使用）

### logback-spring.xml

- ログ設定
- JSON 形式でのログ出力（logstash-logback-encoder 使用）

## 技術スタック

- **Java**: 21
- **Spring Boot**: 3.5.9
- **MyBatis**: 3.0.4
- **MySQL**: 8.0（開発環境）
- **H2**: Docker 環境
- **Gradle**: 8.5
- **Lombok**: 1.18.42
- **Swagger/OpenAPI**: 2.8.13

## ディレクトリ構造

```
web-app-sample/
├── src/
│   ├── main/
│   │   ├── java/                    # ソースコード
│   │   └── resources/               # 設定ファイル、SQL
│   └── test/
│       └── java/                     # テストコード
├── mysql/                            # MySQL初期化SQL
├── documents/                        # ドキュメント
├── Dockerfile                        # Dockerイメージ定義
├── docker-compose.yml               # Docker Compose設定
├── build.gradle                     # Gradleビルド設定
└── Makefile                         # 便利コマンド集
```

## 今後の拡張予定

- 認証・認可機能
- その他のエンティティ（Shop, Reservation 等）の実装
- 統合テストの追加
- CI/CD パイプラインの構築

```

```
