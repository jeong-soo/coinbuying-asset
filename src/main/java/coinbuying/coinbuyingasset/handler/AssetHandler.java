package coinbuying.coinbuyingasset.handler;

import coinbuying.coinbuyingasset.dto.ResponseWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AssetHandler {
    private final AssetService[] assetServices;

    @Autowired
    public AssetHandler(AssetService[] assetServices) {
        this.assetServices = assetServices;
    }

    public Mono<ServerResponse> getWallet(ServerRequest request) {
        final Integer userId = Integer.parseInt(request.pathVariable("userId"));
        List<Mono<UserAssetResponse>> userAssetsToUserAssetResponse = new ArrayList<>();
        for (AssetService<ResponseWalletData> assetService : assetServices) {
            userAssetsToUserAssetResponse.add(assetService.getWallet(userId)
                    .map(assetService::realTimePriceInjection)
                    .flatMap(assetService::fluxCollectToMonoList)
                    .doOnNext(assetService::saveAssetData)
                    .map(assetService::UserResponseAssetDataFilter)
                    .map(assetService::userAssetsToUserAssetResponse));
        }
        Mono<UserAssetResponse> collectUserAssetResponseMono = Flux.fromIterable(userAssetsToUserAssetResponse)
                .flatMap(userAssetResponseMono -> userAssetResponseMono)
                .reduce((x, y) -> {
                    List<UserAssetOne> list = new LinkedList<>();
                    for (UserAssetOne userAssetOne : x.getData()) list.add(userAssetOne);
                    for (UserAssetOne userAssetOne : y.getData()) list.add(userAssetOne);
                    return UserAssetResponse.createUserAssetResponse(x.getInsertDt(), list);
                });

        return ok().contentType(MediaType.APPLICATION_JSON).body(collectUserAssetResponseMono, UserAssetResponse.class);
    }
}
