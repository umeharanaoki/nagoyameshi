INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL_FREE');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_GENERAL_PREMIUM');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_ADMIN');

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (1, '侍 太郎', 'サムライ タロウ', '123-4567', '架空都市1-1-1', '090-1234-5678', '1995-05-20', 'taro.samurai@sample.com', 'password', 1, 1);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (2, '侍 一馬', 'サムライ イチマ', '234-5678', '架空市2-2-2', '090-1234-5678', '1988-09-15', 'ichima.samurai@sample.com', 'password', 2, 1);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (3, '侍 美佳', 'サムライ ミカ', '345-6789', '架空町3-3-3', '090-1234-5678', '1982-03-10', 'mika.samurai@sample.com', 'password', 1, 0);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (4, '侍 花子', 'サムライ ハナコ', '456-7890', '架空村4-4-4', '090-1234-5678', '1957-11-28', 'hanako.samurai@sample.com', 'password', 3, 1);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (5, '侍 修一', 'サムライ シュウイチ', '567-8901', '架空市5-5-5', '090-1234-5678', '1980-07-03', 'shuichi.samurai@sample.com', 'password', 1, 0);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (6, '侍 佐藤', 'サムライ サトウ', '678-9012', '架空区6-6-6', '090-1234-5678', '1984-01-22', 'sato.samurai@sample.com', 'password', 2, 1);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (7, '侍 真司', 'サムライ シンジ', '789-0123', '架空町7-7-7', '090-1234-5678', '1989-06-14', 'shinji.samurai@sample.com', 'password', 1, 1);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (8, '侍 義夫', 'サムライ ヨシオ', '890-1234', '架空村8-8-8', '090-1234-5678', '1983-12-07', 'yoshio.samurai@sample.com', 'password', 2, 0);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (9, '侍 美咲', 'サムライ ミサキ', '901-2345', '架空市9-9-9', '090-1234-5678', '2006-04-18', 'misaki.samurai@sample.com', 'password', 1, 1);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (10, '侍 大介', 'サムライ ダイスケ', '012-3456', '架空区10-10-10', '090-1234-5678', '1991-08-25', 'daisuke.samurai@sample.com', 'password', 2, 0);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, birthday, email, password, role_id, enabled) VALUES (13, '侍 ユリナ', 'サムライ ユリナ', '456-7890', '架空村4-4-4', '090-1234-5678', '1995-06-06', 'yurina.samurai@sample.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, 1);

INSERT IGNORE INTO categories (id, name) VALUES (1, '居酒屋');
INSERT IGNORE INTO categories (id, name) VALUES (2, '焼肉');
INSERT IGNORE INTO categories (id, name) VALUES (3, '寿司');
INSERT IGNORE INTO categories (id, name) VALUES (4, 'カレー');
INSERT IGNORE INTO categories (id, name) VALUES (5, '喫茶店');
INSERT IGNORE INTO categories (id, name) VALUES (6, '中華料理');
INSERT IGNORE INTO categories (id, name) VALUES (7, 'イタリア料理');
INSERT IGNORE INTO categories (id, name) VALUES (8, 'フランス料理');
INSERT IGNORE INTO categories (id, name) VALUES (9, 'スペイン料理');
INSERT IGNORE INTO categories (id, name) VALUES (10, '韓国料理');
INSERT IGNORE INTO categories (id, name) VALUES (11, 'アジア料理');
INSERT IGNORE INTO categories (id, name) VALUES (12, '海鮮');
INSERT IGNORE INTO categories (id, name) VALUES (13, 'ステーキ');
INSERT IGNORE INTO categories (id, name) VALUES (14, 'ハンバーガー');
INSERT IGNORE INTO categories (id, name) VALUES (15, 'ハンバーグ');
INSERT IGNORE INTO categories (id, name) VALUES (16, 'そば');
INSERT IGNORE INTO categories (id, name) VALUES (17, 'ラーメン');
INSERT IGNORE INTO categories (id, name) VALUES (18, 'うどん');
INSERT IGNORE INTO categories (id, name) VALUES (19, 'お好み焼き');
INSERT IGNORE INTO categories (id, name) VALUES (20, 'バー');
INSERT IGNORE INTO categories (id, name) VALUES (21, 'パン');
INSERT IGNORE INTO categories (id, name) VALUES (22, 'スイーツ');
INSERT IGNORE INTO categories (id, name) VALUES (23, '焼き鳥');
INSERT IGNORE INTO categories (id, name) VALUES (24, '天ぷら');
INSERT IGNORE INTO categories (id, name) VALUES (25, '揚げ物');
INSERT IGNORE INTO categories (id, name) VALUES (26, '丼物');
INSERT IGNORE INTO categories (id, name) VALUES (27, '和食');
INSERT IGNORE INTO categories (id, name) VALUES (28, 'うなぎ');

INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (1, 1, '味噌煮込み 一刻', 'restaurant01.jpg', '460-0001', '愛知県名古屋市中区栄1-1-1', '052-1111-1111', '11:00', '21:00', 2000, 5000, 'こだわりの味噌で煮込んだ絶品の味噌煮込みを提供。アットホームな雰囲気でお待ちしております。', 30, '味噌煮込み, ごはん, アットホーム');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (2, 2, '台湾ラーメン 蓮', 'restaurant02.jpg', '460-0002', '愛知県名古屋市中区栄2-2-2', '052-2222-2222', '11:30', '22:00', 2000, 5000, '本場の台湾ラーメンを提供するお店。こだわりのスープと自家製麺が自慢です。', 25, '台湾ラーメン, 麺, こだわり');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (3, 3, '味噌カツ 三春', 'restaurant03.jpg', '460-0003', '愛知県名古屋市中区栄3-3-3', '052-3333-3333', '11:00', '20:30', 2000, 5000, 'サクサクの衣とジューシーな味噌カツが自慢のお店。地元の人に愛される味をご堪能ください。', 40, '味噌カツ, カツ, ジューシー');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (4, 4, 'あんかけスパゲッティ 茜', 'restaurant04.jpg', '460-0004', '愛知県名古屋市中区栄4-4-4', '052-4444-4444', '11:30', '21:30', 1000, 5000, '濃厚なあんかけソースが特徴のスパゲッティ専門店。リーズナブルで美味しい一品をご提供します。', 35, 'あんかけスパゲッティ, イタリアン, リーズナブル');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (5, 5, '手羽先の名店 かもの屋', 'restaurant05.jpg', '460-0005', '愛知県名古屋市中区栄5-5-5', '052-5555-5555', '17:00', '23:00', 1000, 4000, '名古屋風手羽先の専門店。ジューシーで香ばしい手羽先を心ゆくまでお楽しみください。', 28, '手羽先, 居酒屋, ジューシー');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (6, 6, '味噌炭火焼肉 こはな', 'restaurant06.jpg', '460-0006', '愛知県名古屋市中区栄6-6-6', '052-6666-6666', '18:00', '00:00', 3000, 6000, 'こだわりの味噌ダレで炭火焼肉を楽しむお店。新鮮な肉と絶妙な調理法が自慢です。', 20, '炭火焼肉, 味噌, こだわり');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (7, 7, '手作りうどん 蓮華', 'restaurant07.jpg', '460-0007', '愛知県名古屋市中区栄7-7-7', '052-7777-7777', '11:00', '20:00', 1000, 4000, '自家製の手打ちうどんが自慢のお店。優しい出汁とコシのある麺をお楽しみください。', 45, '手作りうどん, 和食, 優しい');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (8, 8, '味噌串カツ 串匠', 'restaurant08.jpg', '460-0008', '愛知県名古屋市中区栄8-8-8', '052-8888-8888', '17:30', '22:30', 2000, 5000, '名古屋名物の味噌串カツを提供するお店。サクサクの衣と味噌の相性が抜群です。', 30, '味噌串カツ, カツ, サクサク');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (9, 9, 'きしめん専門店 はるや', 'restaurant09.jpg', '460-0009', '愛知県名古屋市中区栄9-9-9', '052-9999-9999', '12:00', '19:30', 1000, 4000, '名古屋の郷土料理、きしめんを存分に味わえるお店。シンプルながらも風味豊かな逸品です。', 40, 'きしめん, 郷土料理, シンプル');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (10, 10, '味噌カレー こふく', 'restaurant10.jpg', '460-0010', '愛知県名古屋市中区栄10-10-10', '052-1010-1010', '11:30', '20:30', 2000, 5000, 'こだわりの味噌とカレーが融合した絶品の味噌カレーを提供。スパイスの香りが食欲をそそります。', 35, '味噌カレー, カレー, スパイス');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (11, 11, 'みそかつ亭', 'restaurant11.jpg', '460-0011', '愛知県名古屋市中区栄11-11-11', '052-1111-1111', '11:30', '20:30', 2000, 5000, '名古屋の味噌かつをリーズナブルに楽しめるお店。サクサクの衣と濃厚な味噌ソースが自慢です。', 35, '味噌かつ, リーズナブル, サクサク');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (12, 12, '台湾小吃屋 台湾小', 'restaurant12.jpg', '460-0012', '愛知県名古屋市中区栄12-12-12', '052-1212-1212', '11:00', '21:00', 2000, 5000, '台湾の風味豊かな小吃（点心）を楽しめるアットホームなお店。本場の味をご堪能ください。', 30, '台湾料理, アットホーム, 点心');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (13, 13, '味噌串焼き 串屋', 'restaurant03.jpg', '460-0013', '愛知県名古屋市中区栄13-13-13', '052-1313-1313', '18:00', '00:00', 1000, 4000, '名古屋名物の味噌串焼きが味わえるおしゃれな串焼き屋。リーズナブルでボリューム満点です。', 25, '味噌串焼き, 串焼き, おしゃれ');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (14, 14, '手打ちうどん 一滴', 'restaurant04.jpg', '460-0014', '愛知県名古屋市中区栄14-14-14', '052-1414-1414', '11:00', '20:00', 1000, 4000, 'こだわりの手打ちうどんが楽しめるお店。優しい出汁と自家製の麺が自慢です。', 40, '手打ちうどん, 和食, 出汁');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (15, 15, '味噌カツカレー かつ善', 'restaurant05.jpg', '460-0015', '愛知県名古屋市中区栄15-15-15', '052-1515-1515', '11:30', '20:30', 2000, 5000, '味噌カツとカレーの絶妙なマリアージュを楽しめるお店。ボリューム満点で大満足の一皿です。', 35, '味噌カツカレー, ボリューム, マリアージュ');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (16, 16, '名古屋味噌ラーメン みそや', 'restaurant06.jpg', '460-0016', '愛知県名古屋市中区栄16-16-16', '052-1616-1616', '11:30', '21:00', 2000, 5000, '名古屋の風味豊かな味噌ラーメンが楽しめる老舗のお店。こだわりの味をご賞味ください。', 30, '味噌ラーメン, 風味豊か, 老舗');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (17, 17, 'あんかけシチュー ことぶき', 'restaurant07.jpg', '460-0017', '愛知県名古屋市中区栄17-17-17', '052-1717-1717', '11:00', '21:00', 1000, 4000, '濃厚なあんかけソースが特徴のシチュー専門店。ホッとする味わいを提供しております。', 28, 'あんかけシチュー, 濃厚, ホッとする');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (18, 18, '手羽先BAR やまと', 'restaurant08.jpg', '460-0018', '愛知県名古屋市中区栄18-18-18', '052-1818-1818', '17:00', '23:00', 1000, 4000, '地元で愛される手羽先の専門店。こだわりのソースで味わう極上の手羽先をご提供します。', 28, '手羽先, 地元, こだわり');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (19, 19, '味噌焼きそば 風花', 'restaurant09.jpg', '460-0019', '愛知県名古屋市中区栄19-19-19', '052-1919-1919', '11:30', '20:30', 1000, 4000, '名古屋風の味噌焼きそばが楽しめるお店。モチモチの麺とコクのある味噌が絶妙なバランスです。', 35, '味噌焼きそば, 名古屋風, バランス');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (20, 20, '味噌寿司専門店 こはる', 'restaurant02.jpg', '460-0020', '愛知県名古屋市中区栄20-20-20', '052-2020-2020', '18:00', '00:00', 3000, 6000, '新しい味噌の楽しみ方、味噌寿司を提供するお店。新鮮なネタと味噌の風味が絶品です。', 20, '味噌寿司, 新しい, 風味');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (21, 21, 'しゃぶしゃぶ温野菜', 'restaurant01.jpg', '460-0021', '愛知県名古屋市中区栄21-21-21', '052-2121-2121', '17:00', '22:00', 3000, 6000, '新鮮な野菜とこだわりの肉で楽しむしゃぶしゃぶが自慢のお店。健康を意識したメニューも豊富です。', 40, 'しゃぶしゃぶ, 温野菜, 健康');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (22, 22, 'イタリアンダイニング ベッラ', 'restaurant02.jpg', '460-0022', '愛知県名古屋市中区栄22-22-22', '052-2222-2222', '11:30', '21:30', 4000, 8000, '本場イタリアの味を味わえるイタリアンダイニング。厳選された素材とシェフの腕前が光ります。', 30, 'イタリアン, ダイニング, 本場');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (23, 23, '和風バル 花鳥風月', 'restaurant03.jpg', '460-0023', '愛知県名古屋市中区栄23-23-23', '052-2323-2323', '18:00', '00:00', 4000, 7000, '和風とモダンが融合した雰囲気のバル。季節感あふれる料理とこだわりの日本酒が楽しめます。', 25, '和風, バル, 季節感');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (24, 24, 'カフェ ラ・パンディ', 'restaurant04.jpg', '460-0024', '愛知県名古屋市中区栄24-24-24', '052-2424-2424', '10:00', '18:00', 2000, 4000, 'ここでしか味わえない特製のパンケーキが自慢のカフェ。アートな雰囲気でゆったりとお過ごしください。', 20, 'カフェ, パンケーキ, アート');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (25, 25, '韓国料理 京都屋', 'restaurant05.jpg', '460-0025', '愛知県名古屋市中区栄25-25-25', '052-2525-2525', '11:30', '21:00', 3000, 5000, '本場韓国の味を味わえる韓国料理店。新鮮な食材と本格的な味付けでお楽しみいただけます。', 30, '韓国料理, 本格的, 新鮮');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (26, 26, '寿司と和食 さくら', 'restaurant06.jpg', '460-0026', '愛知県名古屋市中区栄26-26-26', '052-2626-2626', '12:00', '22:00', 3000, 6000, '新鮮なネタと職人の技が融合した和食と寿司のお店。ここでしか味わえない逸品が揃っています。', 25, '寿司, 和食, 逸品');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (27, 27, '串焼きとワイン ぶどう', 'restaurant07.jpg', '460-0027', '愛知県名古屋市中区栄27-27-27', '052-2727-2727', '17:00', '23:00', 3000, 6000, '串焼きとワインが楽しめるおしゃれなお店。美味しい料理とワインで素敵な時間をお過ごしください。', 30, '串焼き, ワイン, おしゃれ');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (28, 28, '洋食とハンバーグ ジョイフル', 'restaurant08.jpg', '460-0028', '愛知県名古屋市中区栄28-28-28', '052-2828-2828', '11:00', '20:00', 2000, 4000, 'ジューシーでボリュームたっぷりなハンバーグが自慢の洋食レストラン。家庭的な雰囲気でお楽しみいただけます。', 35, 'ハンバーグ, 洋食, 家庭的');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (29, 22, 'カレーとインド料理 マハラジャ', 'restaurant09.jpg', '460-0029', '愛知県名古屋市中区栄29-29-29', '052-2929-2929', '11:30', '21:30', 2000, 5000, '本格的なインドカレーが楽しめるお店。スパイスの香りと深い味わいが魅力です。', 30, 'カレー, インド料理, 本格的');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (30, 21, '焼肉と韓国料理 グリル', 'restaurant10.jpg', '460-0030', '愛知県名古屋市中区栄30-30-30', '052-3030-3030', '17:00', '23:00', 3000, 6000, '新鮮な焼肉と本格的な韓国料理が楽しめるグリルレストラン。こだわりの食材で贅沢なひとときをお過ごしください。', 25, '焼肉, 韓国料理, グリル');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (31, 1, '寿司屋 なごや', 'restaurant01.jpg', '460-0031', '愛知県名古屋市中区栄31-31-31', '052-3131-3131', '12:00', '22:00', 4000, 7000, '新鮮なネタと繊細な握りが魅力の寿司屋。シンプルで美味しい日本の味をお楽しみください。', 25, '寿司, 繊細, シンプル');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (32, 2, '和牛焼肉 あづまや', 'restaurant02.jpg', '460-0032', '愛知県名古屋市中区栄32-32-32', '052-3232-3232', '17:00', '23:00', 4000, 8000, '最高級の和牛を贅沢に味わえる焼肉店。上質なお肉とこだわりの調理法が絶品です。', 20, '和牛, 焼肉, 贅沢');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (33, 3, '洋食 クラシックダイニング', 'restaurant03.jpg', '460-0033', '愛知県名古屋市中区栄33-33-33', '052-3333-3333', '11:30', '21:30', 3000, 6000, '洋食のクラシックな味わいが楽しめるダイニング。上品な雰囲気で優雅なひとときをお過ごしください。', 30, '洋食, クラシック, 上品');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (34, 4, '中華料理 蓮華', 'restaurant04.jpg', '460-0034', '愛知県名古屋市中区栄34-34-34', '052-3434-3434', '11:00', '20:00', 3000, 5000, '本格的な中華料理が楽しめるおしゃれなレストラン。一品一品の味わいが心地よく、リーズナブルです。', 35, '中華料理, おしゃれ, リーズナブル');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (35, 5, 'イタリアン ロッソ', 'restaurant05.jpg', '460-0035', '愛知県名古屋市中区栄35-35-35', '052-3535-3535', '18:00', '00:00', 4000, 7000, '本場イタリアの味を感じるイタリアンレストラン。こだわりの食材と美味しいワインがお出迎えします。', 25, 'イタリアン, 本場, ワイン');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (36, 6, '寿司と刺身 つぼみ', 'restaurant06.jpg', '460-0036', '愛知県名古屋市中区栄36-36-36', '052-3636-3636', '12:00', '22:00', 3000, 6000, '新鮮な刺身と握り寿司が楽しめるお店。こだわりの鮮度と味わいが自慢です。', 30, '寿司, 刺身, こだわり');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (37, 7, 'バーガーとクラフトビール ロックダウン', 'restaurant07.jpg', '460-0037', '愛知県名古屋市中区栄37-37-37', '052-3737-3737', '17:00', '23:00', 2000, 4000, 'ボリューム満点のバーガーとクラフトビールが楽しめるおしゃれなレストラン。カジュアルに楽しむことができます。', 35, 'バーガー, クラフトビール, カジュアル');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (38, 8, '和牛しゃぶしゃぶ かたくり', 'restaurant08.jpg', '460-0038', '愛知県名古屋市中区栄38-38-38', '052-3838-3838', '18:00', '00:00', 4000, 8000, '上質な和牛しゃぶしゃぶが味わえるお店。こだわりのダシと新鮮な野菜で贅沢なひとときを提供します。', 25, 'しゃぶしゃぶ, 和牛, 贅沢');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (39, 9, '焼き鳥 とり善', 'restaurant09.jpg', '460-0039', '愛知県名古屋市中区栄39-39-39', '052-3939-3939', '17:00', '23:00', 2000, 5000, '香ばしい焼き鳥が楽しめる居酒屋。新鮮な鳥肉とこだわりのたれでお客様をお迎えします。', 30, '焼き鳥, 香ばしい, こだわり');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (40, 10, 'ラーメン 一点張り', 'restaurant10.jpg', '460-0040', '愛知県名古屋市中区栄40-40-40', '052-4040-4040', '11:30', '21:30', 1000, 3000, 'こだわりの麺と濃厚なスープが自慢のラーメン屋。シンプルで美味しい一杯をご提供します。', 20, 'ラーメン, こだわり, 濃厚');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (41, 11, '海鮮丼 うみこ', 'restaurant01.jpg', '460-0041', '愛知県名古屋市中区栄41-41-41', '052-4141-4141', '12:00', '22:00', 3000, 6000, '新鮮な海の幸が楽しめる海鮮丼専門店。季節ごとに変わるネタが魅力です。', 25, '海鮮丼, 新鮮, 季節');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (42, 12, 'ワインとチーズ ヴィーノ', 'restaurant02.jpg', '460-0042', '愛知県名古屋市中区栄42-42-42', '052-4242-4242', '17:00', '23:00', 4000, 7000, '上質なワインとチーズが楽しめるおしゃれなレストラン。リラックスした雰囲気で優雅なひとときをお過ごしください。', 30, 'ワイン, チーズ, おしゃれ');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (43, 13, '創作和食 ひらく', 'restaurant03.jpg', '460-0043', '愛知県名古屋市中区栄43-43-43', '052-4343-4343', '11:30', '21:30', 3000, 6000, '伝統的な和食に新しいアイデアを加えた創作料理が楽しめるお店。シェフのこだわりが光ります。', 25, '創作和食, 伝統的, アイデア');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (44, 14, 'ベジタリアンカフェ さとみ', 'restaurant04.jpg', '460-0044', '愛知県名古屋市中区栄44-44-44', '052-4444-4444', '10:00', '18:00', 2000, 5000, '新鮮な野菜を使ったヘルシーなベジタリアン料理が楽しめるカフェ。体に優しい美味しさです。', 20, 'ベジタリアン, 野菜, ヘルシー');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (45, 15, '串焼きと日本酒 ひなた', 'restaurant05.jpg', '460-0045', '愛知県名古屋市中区栄45-45-45', '052-4545-4545', '18:00', '00:00', 3000, 5000, 'こだわりの串焼きと日本酒が楽しめるおしゃれな居酒屋。心地よいひとときをお過ごしください。', 30, '串焼き, 日本酒, おしゃれ');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (46, 16, 'ローストビーフ専門店 ロースター', 'restaurant06.jpg', '460-0046', '愛知県名古屋市中区栄46-46-46', '052-4646-4646', '17:00', '23:00', 3000, 6000, 'ジューシーで香ばしいローストビーフが自慢の専門店。肉好きにはたまらない一品です。', 25, 'ローストビーフ, 専門店, 肉好き');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (47, 17, '寿司と和食 ことぶき', 'restaurant07.jpg', '460-0047', '愛知県名古屋市中区栄47-47-47', '052-4747-4747', '12:00', '22:00', 4000, 7000, '新鮮なネタと伝統的な和食が楽しめるお店。和モダンな雰囲気でお客様をお迎えします。', 30, '寿司, 和食, 和モダン');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (48, 18, 'ビストロ エトワール', 'restaurant08.jpg', '460-0048', '愛知県名古屋市中区栄48-48-48', '052-4848-4848', '11:00', '20:00', 3000, 5000, 'フレンチのエッセンスを取り入れたビストロ。素材の味を生かした料理が楽しめます。', 35, 'ビストロ, フレンチ, 素材の味');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (49, 19, 'カフェ プチブラン', 'restaurant09.jpg', '460-0049', '愛知県名古屋市中区栄49-49-49', '052-4949-4949', '10:00', '18:00', 2000, 4000, '心地よいカフェタイムを楽しめるプチブラン。こだわりのコーヒーとスイーツでリラックスしてください。', 35, 'カフェ, コーヒー, スイーツ');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, postal_code, address, phone_number, opening_time, closing_time, min_budget, max_budget, explanation, seating_capacity, keyword) VALUES (50, 20, '麺類と中華料理 千里', 'restaurant10.jpg', '460-0050', '愛知県名古屋市中区栄50-50-50', '052-5050-5050', '11:30', '21:30', 2000, 5000, '本格的な中華麺と料理が楽しめるお店。風味豊かなスープと細麺が自慢です。', 30, '中華料理, 麺類, 本格的');

