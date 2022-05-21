package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.UserAsset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AssetService<T> {
    Mono<T> getWallet(Integer userId);

    Flux<UserAsset> realTimePriceInjection(T responses);

    void saveAssetData(List<UserAsset> userAssets);

    List<UserAsset> addFilterUserShowData(List<UserAsset> userAssets);

    UserAssetResponse userAssetsToUserAssetResponse(List<UserAsset> userAssets);
}
