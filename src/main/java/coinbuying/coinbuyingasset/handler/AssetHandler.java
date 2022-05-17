package coinbuying.coinbuyingasset.handler;


import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.CoinPrice;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.service.AssetService;
import coinbuying.coinbuyingasset.service.CoinPriceService;
import lombok.RequiredArgsConstructor;
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
    private final CoinPriceService coinPriceService;

    @Autowired
    public AssetHandler(AssetService assetService, CoinPriceService coinPriceService) {
        this.assetService = assetService;
        this.coinPriceService = coinPriceService;
    }

    public Mono<ServerResponse> getWallet(ServerRequest request) {

        Mono<UserAssetResponse> response = assetService.getWallet(request)//postService.findContent(request)
                        .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserAsset.class);
    }


    public Mono<ServerResponse> getUpbitWallet(ServerRequest request){

        Mono<UserAssetResponse> upbitWalletDataMono = assetService.getUpbitWallet(request)
                .doOnNext(assetService::updateWallet)
                .map(upbitWalletData -> {
                    List<UserAssetOne> userAssetOnes = new ArrayList<>();
                    for(UpbitWalletCoinPriceDataResponse priceData : upbitWalletData.getPriceDatas()) {
                        userAssetOnes.add(UserAssetOne.builder()
                                .ticker(priceData.getCurrency())
                                .market(MarketType.UPBIT.getName())
                                .volume(priceData.getBalance())
                                .price(100000.0)
                                .total(100000.0 * priceData.getBalance()).build());
                    }
                    return UserAssetResponse.CreateUserAssetResponse(LocalDate.now(), userAssetOnes);
                });
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(upbitWalletDataMono, UserAssetResponse.class);
    }
}
