package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface AssetService {

    Mono<UserAssetResponse> getWallet(ServerRequest serverRequest);
    public Mono<UpbitWalletData> getUpbitWallet(ServerRequest serverRequest);

    public void updateWallet(UpbitWalletData responses);
}
