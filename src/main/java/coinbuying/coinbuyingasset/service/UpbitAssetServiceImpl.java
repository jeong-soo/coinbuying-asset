package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.UpbitApiKey;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;

import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.repository.CoinPriceRepository;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UpbitAssetServiceImpl implements AssetService<UpbitWalletData> {
    private final UserAssetRepository userAssetRepository;
    private final CoinPriceRepository coinPriceRepository;
    private final UserApiKeyService<UpbitApiKey> upbitApiKeyService;

    @Autowired
    public UpbitAssetServiceImpl(UserAssetRepository userAssetRepository, CoinPriceRepository coinPriceRepository, UserApiKeyService<UpbitApiKey> upbitApiKeyService) {
        this.userAssetRepository = userAssetRepository;
        this.coinPriceRepository = coinPriceRepository;
        this.upbitApiKeyService = upbitApiKeyService;
    }

    @Override
    public Mono<UpbitWalletData> getWallet(Integer userId) {
        // TODO : User DB or API 호출하여 키 조회 로직 추가 후 키 주입.
        String accessKey = upbitApiKeyService.getKey().getSecretKey();
        String secretKey = upbitApiKeyService.getKey().getAccessKey();

        String serverUrl = "https://api.upbit.com";

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        WebClient webClient = WebClient.create(serverUrl + "/v1/accounts");

        return webClient.get()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(UpbitWalletCoinPriceDataResponse[].class)
                .map(priceDatas -> {
                    UpbitWalletData res = new UpbitWalletData();
                    res.setPriceDatas(priceDatas);
                    res.setUserId(userId);
                    return res;
                });
    }

    @Override
    public Flux<UserAsset> realTimePriceInjection(final UpbitWalletData upbitWalletData) {
        List<String> tickers = new ArrayList<>();
        HashMap<String, UpbitWalletCoinPriceDataResponse> priceMap = new HashMap<>();
        for (UpbitWalletCoinPriceDataResponse data : upbitWalletData.getPriceDatas()) {
            tickers.add(data.getCurrency());
            priceMap.put(data.getCurrency(), data);
        }
        Flux<UserAsset> selectCoinPriceFlux =
                coinPriceRepository.findByTickerInAndDttmBetween(tickers, LocalDate.now(), LocalDate.now().plusDays(1))
                        .map(coinPrice -> UserAsset.builder()
                                .ticker(coinPrice.getTicker())
                                .price(coinPrice.getPrice())
                                .build());

        Flux<UserAsset> selectUserAssetFlux = userAssetRepository.findByUserIdAndMarket(
                upbitWalletData.getUserId(),
                MarketType.UPBIT.getName());

        return Flux.merge(selectUserAssetFlux, selectCoinPriceFlux)
                .groupBy(userAsset -> userAsset.getTicker())
                .flatMap(groupedFlux -> groupedFlux.reduce((x, y) -> {
                    UserAsset wallet = x, price = y;
                    if (x.getAssetId() == null) wallet = y;
                    price = x;

                    UpbitWalletCoinPriceDataResponse upbitWalletCoinPriceDataResponse = priceMap.get(wallet.getTicker());
                    return UserAsset.builder().assetId(wallet.getAssetId())
                            .ticker(wallet.getTicker())
                            .volume(upbitWalletCoinPriceDataResponse.getBalance())
                            .insertDt(LocalDate.now())
                            .price(price.getPrice()).build();
                }))
                .map(userAsset -> UserAsset.builder().assetId(userAsset.getAssetId())
                        .userId(upbitWalletData.getUserId())
                        .price(userAsset.getPrice())
                        .volume(userAsset.getVolume())
                        .ticker(userAsset.getTicker())
                        .insertDt(userAsset.getInsertDt())
                        .market(MarketType.UPBIT.getName()).build())
                .map(userAsset -> {
                    if (userAsset.getVolume() == null || !priceMap.containsKey(userAsset.getTicker()))
                        return UserAsset.builder().assetId(userAsset.getAssetId())
                                .userId(upbitWalletData.getUserId())
                                .price(userAsset.getPrice())
                                .volume(0.0)
                                .ticker(userAsset.getTicker())
                                .insertDt(userAsset.getInsertDt())
                                .market(MarketType.UPBIT.getName()).build();
                    return userAsset;
                });
    }

    @Override
    public Mono<List<UserAsset>> fluxCollectToMonoList(Flux<UserAsset> userAssetFlux) {
        return userAssetFlux.collectList();
    }

    @Override
    public void saveAssetData(List<UserAsset> userAssets) {
        for (UserAsset userAsset : userAssets) {
            userAssetRepository.save(userAsset).subscribe();
        }
    }

    @Override
    public List<UserAsset> UserResponseAssetDataFilter(List<UserAsset> userAssets) {
        return userAssets.parallelStream()
                .filter(userAsset -> userAsset.getVolume() > 0)
                .filter(userAsset -> userAsset.getPrice() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public UserAssetResponse userAssetsToUserAssetResponse(List<UserAsset> userAssets) {
        return UserAssetResponse.CreateUserAssetResponse(LocalDate.now(),
                userAssets.parallelStream()
                        .map(userAsset -> UserAssetOne.builder()
                                .ticker(userAsset.getTicker())
                                .market(userAsset.getMarket())
                                .price(userAsset.getPrice())
                                .volume(userAsset.getVolume())
                                .total(userAsset.getPrice() * userAsset.getVolume()).build())
                        .collect(Collectors.toList()));
    }
}
