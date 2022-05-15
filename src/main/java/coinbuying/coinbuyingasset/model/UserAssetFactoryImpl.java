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
    public UserAsset userAssetBuilder(Long userId, String ticker, String market, Double price, Double volume, LocalDateTime insertDttm) {
        return UserAsset.builder().ticker(ticker).market(market).price(price).volume(volume).insertDttm(insertDttm).build();
    }

    @Override
    public List<UserAsset> setupListBuilder() {
        return Arrays.asList(
                this.userAssetBuilder(1L, CoinType.BTC.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(2L, CoinType.ETH.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(3L, CoinType.DOGE.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(4L, CoinType.EOS.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(5L, CoinType.XRP.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(6L, CoinType.BTC.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(7L, CoinType.ETH.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(8L, CoinType.DOGE.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(9L, CoinType.EOS.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now()),
                this.userAssetBuilder(10L, CoinType.XRP.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDateTime.now())
        );
    }
}
