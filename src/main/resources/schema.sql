DROP TABLE IF EXISTS user_asset;

CREATE TABLE IF NOT EXISTS user_asset (
    asset_id INT(10) AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT 'asset id',
    user_id INT(10) NOT NULL COMMENT 'user id',
    ticker VARCHAR(6) NOT NULL COMMENT 'coin code',
    market VARCHAR(20) NOT NULL COMMENT 'coin market',
    price DOUBLE(20,5) NOT NULL COMMENT 'coin price',
    volume DOUBLE(20,5) NOT NULL COMMENT 'coin volume',
    insert_dt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);