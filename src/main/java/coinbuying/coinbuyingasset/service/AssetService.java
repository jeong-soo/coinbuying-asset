package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.ResponseWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.UserAsset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AssetService<T extends ResponseWalletData> {
    Mono<T> getWallet(Integer userId);

    Flux<UserAsset> realTimePriceInjection(T responses);

    default Mono<List<UserAsset>> fluxCollectToMonoList(Flux<UserAsset> userAssetFlux) {
        return userAssetFlux.collectList();
    }

    void saveAssetData(List<UserAsset> userAssets);

    List<UserAsset> UserResponseAssetDataFilter(List<UserAsset> userAssets);

    UserAssetResponse userAssetsToUserAssetResponse(List<UserAsset> userAssets);
}
