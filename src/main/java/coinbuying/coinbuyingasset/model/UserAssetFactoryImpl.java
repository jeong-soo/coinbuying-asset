package coinbuying.coinbuyingasset.model;

import coinbuying.coinbuyingasset.entity.CoinType;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class UserAssetFactoryImpl implements UserAssetFactory{
    @Override
    public UserAsset userAssetBuilder(String ticker, String market, Double price, LocalDateTime updateDttm) {
        return UserAsset.builder().ticker(ticker).market(market).price(price).updateDttm(updateDttm).build();
    }

    @Override
    public UserAsset userAssetBuilder(CoinType coinType, MarketType market, Double price, LocalDateTime updateDttm) {
        return this.userAssetBuilder(coinType.getTicker(), market.getName(), price, updateDttm);
    }

    @Override
    public List<UserAsset> setupListBuilder() {
        return Arrays.asList(
                this.userAssetBuilder(CoinType.BTC, MarketType.BITHUMB, 400000000.0, LocalDateTime.now()),
                this.userAssetBuilder(CoinType.ETH, MarketType.BITHUMB, 400000000.0, LocalDateTime.now()),
                this.userAssetBuilder(CoinType.XRP, MarketType.BITHUMB, 400000000.0, LocalDateTime.now()),
                this.userAssetBuilder(CoinType.EOS, MarketType.BITHUMB, 400000000.0, LocalDateTime.now())
        );
    }
}
