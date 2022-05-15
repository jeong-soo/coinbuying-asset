CREATE TABLE IF NOT EXISTS user_asset (
    asset_id PRIMARY KEY COMMENT 'asset id',
    user_id VARCHAR(50) NOT NULL COMMENT '유저 아이디',
    ticker CHAR(6) NOT NULL COMMENT '코인 종목코드',
    market VARCHAR(20) NOT NULL COMMENT '거래소',
    price DOUBLE(20) NOT NULL COMMENT '가격',
    volume DOUBLE(20) NOT NULL COMMENT '보유량',
    insert_dttm DATETIME NOT NULL COMMENT '업데이트시간'
);