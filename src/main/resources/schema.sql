CREATE TABLE IF NOT EXISTS user_asset (
    ticker CHAR(6) NOT NULL COMMENT '코인 종목코드',
    market VARCHAR(20) NOT NULL COMMENT '거래소',
    price DOUBLE(20) NOT NULL COMMENT '가격',
    update_dttm DATETIME NOT NULL COMMENT '업데이트시간',
    PRIMARY KEY(coin_id, market)
);