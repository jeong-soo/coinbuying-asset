package coinbuying.coinbuyingasset.model;

import coinbuying.coinbuyingasset.entity.CoinType;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAssetFactory {
    UserAsset userAssetBuilder(String coin_id, String market, Double price, LocalDateTime updateDttm);
//    UserAsset userAssetBuilder(String coin_id, MarketType market, Double price, LocalDateTime updateDttm);
    UserAsset userAssetBuilder(CoinType coinType, MarketType market, Double price, LocalDateTime updateDttm);
    List<UserAsset> setupListBuilder();
}