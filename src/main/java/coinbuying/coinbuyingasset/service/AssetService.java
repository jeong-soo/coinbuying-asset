package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AssetService {

    Mono<UserAssetResponse> getWallet(ServerRequest serverRequest);
    public Mono<UpbitWalletData> getUpbitWallet(ServerRequest serverRequest);

    public Flux<UserAsset> updateWalletDbAndMapCoinPrice(UpbitWalletData responses);
}
