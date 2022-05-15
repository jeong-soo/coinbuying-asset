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
    public UserAsset userAssetBuilder(String userId, String ticker, String market, Double price, Double volume, LocalDateTime insertDttm) {
        return UserAsset.builder().ticker(ticker).market(market).price(price).volume(volume).insertDttm(insertDttm).build();
    }

    @Override
    public List<UserAsset> setupListBuilder() {
        return Arrays.asList(
                this.userAssetBuilder("test", CoinType.BTC.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.ETH.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.DOGE.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.EOS.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.XRP.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.BTC.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.ETH.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.DOGE.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.EOS.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder("test", CoinType.XRP.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now())
        );
    }
}
