package coinbuying.coinbuyingasset.model;

import coinbuying.coinbuyingasset.entity.CoinType;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAssetFactory {
    UserAsset userAssetBuilder(String userId, String ticker, String market, Double price, Double volume, LocalDateTime insertDttm);
    List<UserAsset> setupListBuilder();
}