# TODO アプリケーション

Spring Boot、Thymeleaf、PostgreSQLを使用したTODO管理アプリケーションです。

## 機能

- ✅ ユーザー認証（ログイン・サインアップ）
- ✅ TODOのCRUD操作（作成・読み取り・更新・削除）
- ✅ 一画面でのTODO管理
- ✅ 完了/未完了の切り替え
- ✅ 統計情報の表示
- ✅ レスポンシブデザイン

## 技術スタック

- **フレームワーク**: Spring Boot 3.5.0
- **テンプレートエンジン**: Thymeleaf
- **データベース**: PostgreSQL
- **ORM**: Spring Data JPA
- **セキュリティ**: Spring Security
- **ビルドツール**: Maven
- **フロントエンド**: Bootstrap 5, Font Awesome

## 前提条件

- Java 17以上
- PostgreSQL 12以上
- Maven 3.6以上

## セットアップ手順

### 1. PostgreSQLデータベースの準備

```bash
# PostgreSQLに接続
psql -U postgres

# データベースを作成
CREATE DATABASE todoapp;

# 接続を終了
\q
```

または、提供されている `database-setup.sql` を実行：

## Dockerが使える環境であれば、コンテナでDBを作成できます
```bash
docker compose up -d
```

```bash
psql -U postgres -f database-setup.sql
```

### 2. アプリケーション設定

`src/main/resources/application.properties` でデータベース接続情報を確認・変更してください：

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todoapp
spring.datasource.username=postgres
spring.datasource.password=password
```

### 3. アプリケーションの起動

```bash
# 依存関係のインストールとアプリケーションの起動
./mvnw spring-boot:run

# または
mvn spring-boot:run
```

### 4. アプリケーションへのアクセス

ブラウザで以下のURLにアクセスしてください：

```
http://localhost:8080
```

## 使用方法

### 1. アカウント作成

1. ブラウザで `http://localhost:8080` にアクセス
2. 「新規登録」ボタンをクリック
3. ユーザー名とパスワードを入力してアカウントを作成

### 2. ログイン

1. 作成したアカウントでログイン
2. TODOリスト画面が表示されます

### 3. TODOの管理

- **作成**: 上部のフォームでタイトルと説明を入力して「追加」ボタンをクリック
- **完了/未完了の切り替え**: 各TODOの「完了」または「未完了」ボタンをクリック
- **編集**: 「編集」ボタンをクリックしてモーダルで編集
- **削除**: 「削除」ボタンをクリック（確認ダイアログが表示されます）

## プロジェクト構造

```
src/
├── main/
│   ├── java/com/example/todo/
│   │   ├── TodoApplication.java          # メインクラス
│   │   ├── config/
│   │   │   └── SecurityConfig.java       # Spring Security設定
│   │   ├── controller/
│   │   │   ├── AuthController.java       # 認証コントローラー
│   │   │   └── TodoController.java       # TODOコントローラー
│   │   ├── entity/
│   │   │   ├── Todo.java                 # TODOエンティティ
│   │   │   └── User.java                 # ユーザーエンティティ
│   │   ├── repository/
│   │   │   ├── TodoRepository.java       # TODOリポジトリ
│   │   │   └── UserRepository.java       # ユーザーリポジトリ
│   │   └── service/
│   │       ├── CustomUserDetailsService.java  # ユーザー詳細サービス
│   │       ├── TodoService.java          # TODOサービス
│   │       └── UserService.java          # ユーザーサービス
│   └── resources/
│       ├── application.properties        # アプリケーション設定
│       └── templates/
│           ├── index.html               # メイン画面
│           ├── login.html               # ログイン画面
│           └── signup.html              # サインアップ画面
└── test/
    └── java/com/example/todo/
        └── TodoApplicationTests.java     # テストクラス
```

## API エンドポイント

### 認証
- `GET /login` - ログイン画面
- `POST /login` - ログイン処理
- `GET /signup` - サインアップ画面
- `POST /signup` - サインアップ処理
- `POST /logout` - ログアウト処理

### TODO管理
- `GET /` - TODOリスト表示
- `POST /todos` - TODO作成
- `POST /todos/{id}/update` - TODO更新
- `POST /todos/{id}/toggle` - 完了状態切り替え
- `POST /todos/{id}/delete` - TODO削除

## セキュリティ機能

- パスワードのハッシュ化（BCrypt）
- CSRF保護（開発用に無効化、本番では有効にすること）
- セッション管理
- ユーザー認証・認可

## 開発時の注意事項

1. **データベース接続**: PostgreSQLサーバーが起動していることを確認
2. **ポート**: デフォルトでポート8080を使用
3. **ログ**: `application.properties`で`spring.jpa.show-sql=true`によりSQL文が出力されます

## トラブルシューティング

### データベース接続エラー
- PostgreSQLサーバーが起動しているか確認
- データベース名、ユーザー名、パスワードが正しいか確認
- `application.properties`の設定を確認

### ポート競合エラー
- 他のアプリケーションがポート8080を使用していないか確認
- `application.properties`に`server.port=8081`を追加してポートを変更

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。
