package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.CoinPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoinPriceService {
    public Mono<CoinPrice> getPriceDatas(UserAssetOne assetOne);
}
