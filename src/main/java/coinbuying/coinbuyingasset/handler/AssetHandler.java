package coinbuying.coinbuyingasset.handler;


import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.service.AssetService;
import coinbuying.coinbuyingasset.service.CoinPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AssetHandler {
    private final AssetService assetService;

    @Autowired
    public AssetHandler(AssetService assetService) {
        this.assetService = assetService;
    }

    public Mono<ServerResponse> getWallet(ServerRequest request) {
        Integer userId = Integer.parseInt(request.pathVariable("userId"));
        Mono<UserAssetResponse> response = assetService.getWallet(request)//postService.findContent(request)
                .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserAsset.class);
    }


    public Mono<ServerResponse> getUpbitWallet(ServerRequest request) {
        Integer userId = Integer.parseInt(request.pathVariable("userId"));
        Mono<UserAssetResponse> upbitWalletDataMono = assetService.getUpbitWallet(request, userId)
                .map(assetService::updateWalletDbAndMapCoinPrice)
                .flatMap(userAssetFlux -> userAssetFlux.collectList())
                .map(userAssets -> {
                    List<UserAssetOne> userAssetOnes = new ArrayList<>();
                    for(UserAsset userAsset : userAssets) {
                        userAssetOnes.add(UserAssetOne.builder()
                                .ticker(userAsset.getTicker())
                                .market(userAsset.getMarket())
                                .price(userAsset.getPrice())
                                .volume(userAsset.getVolume())
                                .total(userAsset.getPrice() * userAsset.getVolume()).build());
                    }
                    return UserAssetResponse.CreateUserAssetResponse(LocalDate.now(), userAssetOnes);
                });
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(upbitWalletDataMono, UserAssetResponse.class);
    }
}
