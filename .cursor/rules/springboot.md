# Spring Boot コーディングルール

本資料は Spring Boot の REST API のコーディングルールです。以下の指示に従ってください。

## (1) 基本ルール

- javadoc やコメントは日本語で記載します。
- 極力変数は変更不要で定義し、final を使用してください。
- 過剰なログ出力は行いません。 正常系ログは AOP/Filter で基本的に行います。異常系のログは ExceptionHandler や catch 句内でのみ出力します。
- Service にロジックを集約させずに、Domain Model や Factory/Converter のような専用責務を持つクラスに処理を分割し、メンテしやすくします。

## (2) パッケージ構成

- フラットパッケージ構成を採用します。
- model クラスについては models ディレクトリ内にレイヤーで区別してまとめます。
- Spring Boot + MyBatis の標準に従ったレイヤー分割でファイルを作成します。(Controller -> Service -> Repository -> Mapper )
- ただし、Mapper のメソッドを呼ぶだけのようなただの wrapper method を Repository で作成しないでください。

  - 上記の場合は Service から直接 Mapper を呼び出してください。(冗長な Repository の省略)
  - Repository は複数のテーブルからデータを取得しまとめて Service に返却するなど、データ操作周りのビジネスロジックを補助する目的で利用するようにしてください。

- components ディレクトリには Bean 定義する共通クラスを定義します。
- utils ディレクトリには DI 管理しない、共通ロジックを定義します。

## (3) Model のレイヤー・変換ルール

- presentation: Controller で利用する Model を定義します。 Request, Query, Response
- domain: Service レイヤーで利用する Model を定義します。 Dto, Result
- infra: Repository/Mapper レイヤーで利用する Model を定義します。 DB Entity/DB 検索用の Dto

- Controller で infra の Model を利用する/Repository, Mapper で presentation の Model を利用することは固く禁止します。
- service レイヤーで presentation/infra モデルを利用することは許容します。

- シンプルな Model 変換は Model 内で static method で実装してください
- 複雑な Model 変換については Conveter/Factory という補助クラスを Spring Bean として用意して作成し、そちらで変換するようにしてください。
  - Factory: Model の生成に特化したクラス Service -> Repository で必要な Model を生成する
  - Converter: Model 間の変換に特化したクラス 基本的には戻し(Repository -> Service/ Service -> Controller)の Model 変換で利用
