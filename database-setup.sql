-- PostgreSQL データベース作成スクリプト
-- このスクリプトを実行してデータベースを作成してください

-- データベースの作成
CREATE DATABASE todoapp;

-- データベースに接続
\c todoapp;

-- ユーザーテーブル（Spring Boot JPA が自動作成しますが、参考として記載）
-- CREATE TABLE users (
--     id BIGSERIAL PRIMARY KEY,
--     username VARCHAR(255) UNIQUE NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     role VARCHAR(50) NOT NULL DEFAULT 'USER'
-- );

-- TODOテーブル（Spring Boot JPA が自動作成しますが、参考として記載）
-- CREATE TABLE todos (
--     id BIGSERIAL PRIMARY KEY,
--     title VARCHAR(255) NOT NULL,
--     description TEXT,
--     completed BOOLEAN NOT NULL DEFAULT FALSE,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     user_id BIGINT NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
-- );

-- インデックスの作成（パフォーマンス向上のため）
-- CREATE INDEX idx_todos_user_id ON todos(user_id);
-- CREATE INDEX idx_todos_completed ON todos(completed);
-- CREATE INDEX idx_todos_created_at ON todos(created_at);

-- 注意: 
-- 1. PostgreSQLサーバーが起動していることを確認してください
-- 2. application.propertiesのデータベース設定を確認してください
-- 3. Spring Boot JPA の hibernate.ddl-auto=update により、テーブルは自動作成されます
