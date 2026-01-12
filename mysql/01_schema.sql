-- データベース作成（既存の場合はスキップ）
CREATE DATABASE IF NOT EXISTS web_sample_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE web_sample_db;

-- usersテーブル
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ユーザーID',
    name VARCHAR(100) NOT NULL COMMENT 'ユーザー名',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT 'メールアドレス',
    phone_number VARCHAR(20) COMMENT '電話番号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ユーザーテーブル';

-- shopsテーブル
CREATE TABLE IF NOT EXISTS shops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '店舗ID',
    name VARCHAR(200) NOT NULL COMMENT '店舗名',
    address VARCHAR(500) COMMENT '住所',
    phone_number VARCHAR(20) COMMENT '電話番号',
    description TEXT COMMENT '店舗説明',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店舗テーブル';

-- user_reservationsテーブル
CREATE TABLE IF NOT EXISTS user_reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '予約ID',
    user_id BIGINT NOT NULL COMMENT 'ユーザーID',
    shop_id BIGINT NOT NULL COMMENT '店舗ID',
    reservation_date DATE NOT NULL COMMENT '予約日',
    reservation_time TIME NOT NULL COMMENT '予約時間',
    number_of_people INT NOT NULL COMMENT '人数',
    status VARCHAR(20) NOT NULL DEFAULT 'RESERVED' COMMENT '予約ステータス（RESERVED:予約済み, CANCELLED:キャンセル, COMPLETED:来店済み）',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    INDEX idx_user_id (user_id),
    INDEX idx_shop_id (shop_id),
    INDEX idx_reservation_date (reservation_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ユーザー予約テーブル';
