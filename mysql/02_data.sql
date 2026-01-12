-- ローカル環境用の初期データ投入
USE web_sample_db;

-- usersテーブルの初期データ
INSERT INTO users (name, email, phone_number) VALUES
('山田太郎', 'yamada.taro@example.com', '090-1234-5678'),
('佐藤花子', 'sato.hanako@example.com', '080-2345-6789'),
('鈴木一郎', 'suzuki.ichiro@example.com', '070-3456-7890'),
('田中美咲', 'tanaka.misaki@example.com', '090-4567-8901'),
('伊藤健太', 'ito.kenta@example.com', '080-5678-9012')
ON DUPLICATE KEY UPDATE name=name;

-- shopsテーブルの初期データ
INSERT INTO shops (name, address, phone_number, description) VALUES
('レストラン イタリアン ロマーノ', '東京都渋谷区道玄坂1-2-3', '03-1234-5678', '本格的なイタリアンを提供するレストラン。厳選された食材とワインのペアリングが自慢です。'),
('和食 さくら', '東京都新宿区新宿3-1-1', '03-2345-6789', '季節の素材を活かした伝統的な和食料理店。落ち着いた雰囲気でゆっくりとお食事をお楽しみいただけます。'),
('ステーキハウス グランデ', '東京都港区赤坂5-6-7', '03-3456-7890', '最高級の国産和牛を使用したステーキレストラン。シェフが選んだ最良の肉を提供します。'),
('カフェ パリジャン', '東京都世田谷区三軒茶屋2-3-4', '03-4567-8901', 'フレンチスタイルのカフェレストラン。ランチからディナーまで、気軽にフレンチを楽しめます。'),
('寿司 本格', '東京都中央区銀座4-5-6', '03-5678-9012', '職人が握る本格的な江戸前寿司。新鮮なネタと熟練の技で最高のひとときを。')
ON DUPLICATE KEY UPDATE name=name;

-- user_reservationsテーブルの初期データ
INSERT INTO user_reservations (user_id, shop_id, reservation_date, reservation_time, number_of_people, status) VALUES
(1, 1, '2024-12-20', '19:00:00', 2, 'RESERVED'),
(1, 2, '2024-12-15', '18:30:00', 4, 'COMPLETED'),
(2, 3, '2024-12-22', '20:00:00', 2, 'RESERVED'),
(2, 1, '2024-12-10', '19:30:00', 2, 'COMPLETED'),
(3, 4, '2024-12-25', '12:00:00', 3, 'RESERVED'),
(3, 2, '2024-12-05', '18:00:00', 2, 'CANCELLED'),
(4, 5, '2024-12-18', '19:00:00', 2, 'RESERVED'),
(4, 3, '2024-12-12', '20:30:00', 4, 'COMPLETED'),
(5, 1, '2024-12-28', '19:00:00', 4, 'RESERVED'),
(5, 4, '2024-12-08', '13:00:00', 2, 'COMPLETED')
ON DUPLICATE KEY UPDATE user_id=user_id;
