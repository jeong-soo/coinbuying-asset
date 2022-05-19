package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;

import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.repository.CoinPriceRepository;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDate;
import java.util.*;


@Service
public class AssetServiceImpl implements AssetService<UpbitWalletData> {
    private final UserAssetRepository userAssetRepository;
    private final CoinPriceRepository coinPriceRepository;

    @Autowired
    public AssetServiceImpl(UserAssetRepository userAssetRepository, CoinPriceRepository coinPriceRepository) {
        this.userAssetRepository = userAssetRepository;
        this.coinPriceRepository = coinPriceRepository;
    }

    @Override
    public Mono<UpbitWalletData> getWallet(Integer userId) {
        String accessKey = "sZiMJou0evyRRV3GB6Nrtgo1a9fuEU5OnSjBHRqM";//System.getenv("UPBIT_OPEN_API_ACCESS_KEY");
        String secretKey = "898Usfgbf54lJDiVs7nBL3mHxLElrBoZRAPnW7dx";//System.getenv("UPBIT_OPEN_API_SECRET_KEY");
        String serverUrl = "https://api.upbit.com";//System.getenv("UPBIT_OPEN_API_SERVER_URL");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        JSONObject jsonObj = null;
        String retVal = "";

        WebClient webClient = WebClient.create(serverUrl + "/v1/accounts");

        return webClient.get()
                .header("Content-Type", "application/json")
                .header("Authorization", authenticationToken)
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
    public Flux<UserAsset> getMeargeRealTimeData(UpbitWalletData upbitWalletData) {
        Arrays.sort(upbitWalletData.getPriceDatas(), (x, y) -> x.getCurrency().compareTo(y.getCurrency()));
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
        Flux<UserAsset> selectUserAssetFlux = userAssetRepository.findByUserIdAndMarketAndInsertDt(
                upbitWalletData.getUserId(),
                MarketType.UPBIT.getName(),
                LocalDate.now()
        );
        return Flux.merge(selectUserAssetFlux, selectCoinPriceFlux)
                .groupBy(userAsset -> userAsset.getTicker())
                .flatMap(groupedFlux -> groupedFlux.reduce((x, y) -> {
                    UserAsset wallet = x, price = y;
                    if (x.getAssetId() == null) {
                        wallet = y; price = x;
                    }
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
                    if(userAsset.getVolume() == null || !priceMap.containsKey(userAsset.getTicker()))
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
    public void saveAssetData(Flux<UserAsset> userAssetFlux) {
        userAssetFlux.doOnNext(userAsset -> userAssetRepository.save(userAsset).subscribe());
    }

    @Override
    public Flux<UserAsset> addFilterUserShowData(Flux<UserAsset> userAssetFlux) {
        return userAssetFlux.filter(userAsset -> userAsset.getVolume() > 0);
    }

    @Override
    public UserAssetResponse userAssetsToUserAssetResponse(List<UserAsset> userAssets) {
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
    }
}
