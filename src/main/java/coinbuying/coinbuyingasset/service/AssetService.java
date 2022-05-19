package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.UserAsset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AssetService<T> {
    public Mono<T> getWallet(Integer userId);

    public Flux<UserAsset> getMeargeRealTimeData(T responses);

    public void saveAssetData(Flux<UserAsset> userAssets);

    public Flux<UserAsset> addFilterUserShowData(Flux<UserAsset> userAssetFlux);

    public UserAssetResponse userAssetsToUserAssetResponse(List<UserAsset> userAssets);
}
