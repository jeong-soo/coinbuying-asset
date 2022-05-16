package coinbuying.coinbuyingasset.model;

import coinbuying.coinbuyingasset.entity.CoinType;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class UserAssetFactoryImpl implements UserAssetFactory{
    @Override
    public UserAsset userAssetBuilder(int userId, String ticker, String market, Double price, Double volume, LocalDate insertDttm) {
        return UserAsset.builder()
                .userId(userId)
                .ticker(ticker).market(market).price(price).volume(volume).insertDttm(insertDttm).build();
    }

    @Override
    public List<UserAsset> setupListBuilder() {
        return Arrays.asList(
                this.userAssetBuilder(1, CoinType.BTC.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(1, CoinType.ETH.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(1, CoinType.DOGE.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(5, CoinType.EOS.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(5, CoinType.XRP.getTicker(), MarketType.BITHUMB.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(5, CoinType.BTC.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(5, CoinType.ETH.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(2, CoinType.DOGE.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(2, CoinType.EOS.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDate.now()),
                this.userAssetBuilder(5, CoinType.XRP.getTicker(), MarketType.UPBIT.getName(), 400000000.0, 1.2, LocalDate.now())
        );
    }
}
