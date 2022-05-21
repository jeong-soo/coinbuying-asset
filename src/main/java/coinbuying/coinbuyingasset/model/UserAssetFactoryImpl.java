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
    public UserAsset userAssetBuilder(int userId, String ticker, String market, Double price, Double volume, LocalDate insertDt) {
        return UserAsset.builder()
                .userId(userId)
                .ticker(ticker).market(market).price(price).volume(volume).insertDt(insertDt).build();
    }

    @Override
    public List<UserAsset> setupListBuilder() {
        return Arrays.asList(
//                this.userAssetBuilder(123123, "APENFT", MarketType.BITHUMB.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "BTC", MarketType.BITHUMB.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "ETH", MarketType.BITHUMB.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "KRW", MarketType.BITHUMB.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "LINK", MarketType.BITHUMB.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "SGB", MarketType.UPBIT.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "USDT", MarketType.UPBIT.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, "WIN", MarketType.UPBIT.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, CoinType.EOS.getTicker(), MarketType.UPBIT.getName(), 100000000.0, 1.2, LocalDate.now()),
//                this.userAssetBuilder(123123, CoinType.XRP.getTicker(), MarketType.UPBIT.getName(), 100000000.0, 1.2, LocalDate.now())
        );
    }
}
