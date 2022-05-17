package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.UserAsset;
import org.json.simple.JSONObject;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetService {

    Mono<UserAssetResponse> getWallet(ServerRequest serverRequest);
    public String getUpbitWallet(ServerRequest serverRequest);
}
