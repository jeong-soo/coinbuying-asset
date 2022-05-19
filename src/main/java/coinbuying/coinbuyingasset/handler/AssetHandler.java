package coinbuying.coinbuyingasset.handler;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AssetHandler {
    private final AssetService<UpbitWalletData> upbitAssetService;

    @Autowired
    public AssetHandler(AssetService assetService) {
        this.upbitAssetService = assetService;
    }

    public Mono<ServerResponse> getWallet(ServerRequest request) {
        final Integer userId = Integer.parseInt(request.pathVariable("userId"));
        Mono<UserAssetResponse> upbitWalletDataMono = upbitAssetService.getWallet(userId)
                .map(upbitAssetService::getMeargeRealTimeData)
                .doOnNext(upbitAssetService::saveAssetData)
                .map(upbitAssetService::addFilterUserShowData)
                .flatMap(userAssetFlux -> userAssetFlux.collectList())
                .map(upbitAssetService::userAssetsToUserAssetResponse);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(upbitWalletDataMono, UserAssetResponse.class);
    }
}
