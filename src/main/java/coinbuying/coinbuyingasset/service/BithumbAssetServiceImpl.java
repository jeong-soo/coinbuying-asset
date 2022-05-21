package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.BithumbWalletData;
import coinbuying.coinbuyingasset.dto.UpbitApiKey;
import coinbuying.coinbuyingasset.dto.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.repository.CoinPriceRepository;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BithumbAssetServiceImpl implements AssetService<BithumbWalletData> {
    private final UserAssetRepository userAssetRepository;
    private final CoinPriceRepository coinPriceRepository;
    private final UserApiKeyService<UpbitApiKey> upbitApiKeyService;

    @Autowired
    public BithumbAssetServiceImpl(UserAssetRepository userAssetRepository, CoinPriceRepository coinPriceRepository, UserApiKeyService<UpbitApiKey> upbitApiKeyService) {
        this.userAssetRepository = userAssetRepository;
        this.coinPriceRepository = coinPriceRepository;
        this.upbitApiKeyService = upbitApiKeyService;
    }

    @Override
    public Mono<BithumbWalletData> getWallet(Integer userId) {
        return Mono.just(new BithumbWalletData());
    }

    @Override
    public Flux<UserAsset> realTimePriceInjection(final BithumbWalletData bithumbWalletData) {
        return Flux.just(UserAsset.builder().build());
    }

    @Override
    public Mono<List<UserAsset>> fluxCollectToMonoList(Flux<UserAsset> userAssetFlux) {
        return userAssetFlux.collectList();
    }

    @Override
    public void saveAssetData(List<UserAsset> userAssets) {

    }

    @Override
    public List<UserAsset> UserResponseAssetDataFilter(List<UserAsset> userAssets) {
        return userAssets;
    }

    @Override
    public UserAssetResponse userAssetsToUserAssetResponse(List<UserAsset> userAssets) {
        return UserAssetResponse.CreateUserAssetResponse(LocalDate.now(), new ArrayList<>());
    }
}
