package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;

import coinbuying.coinbuyingasset.entity.MarketType;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.repository.R2dbcEntityAssetRepository;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    UserAssetRepository userAssetRepository;

    R2dbcEntityAssetRepository r2dbcAssetRepo;

    @Autowired
    public AssetServiceImpl(UserAssetRepository userAssetRepository, R2dbcEntityAssetRepository r2dbcAssetRepo) {
        this.userAssetRepository = userAssetRepository;
        this.r2dbcAssetRepo = r2dbcAssetRepo;
    }

    @Override
    public Mono<UserAssetResponse> getWallet(ServerRequest serverRequest) {
        int userId = Integer.parseInt(serverRequest.pathVariable("userId"));

         return userAssetRepository.findByUserIdAndInsertDt(userId, LocalDate.now())
                 .flatMap(asset -> {
                     double total = asset.getPrice() * asset.getVolume();
                     return Mono.just(new UserAssetOne(asset.getTicker(), asset.getMarket(), asset.getPrice(), asset.getVolume(), total));
                 }).collectList()
                 .map(p->{
                     return UserAssetResponse.CreateUserAssetResponse(LocalDate.now(), p);
                 });
    }

    @Override
    public Mono<UpbitWalletData> getUpbitWallet(ServerRequest serverRequest) {
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
                    res.setUserId(123123);
                    return res; });
    }

    @Override
    public void updateWallet(UpbitWalletData upbitWalletData) {
        Arrays.sort(upbitWalletData.getPriceDatas(), (x, y) -> x.getCurrency().compareTo(y.getCurrency()));
        List<String> tickers = new ArrayList<>();
        Map<String, UpbitWalletCoinPriceDataResponse> priceMap = new HashMap<>();
        for(UpbitWalletCoinPriceDataResponse data : upbitWalletData.getPriceDatas()) {
            tickers.add(data.getCurrency());
        }
        userAssetRepository.findByUserIdAndMarketAndInsertDt(
                upbitWalletData.getUserId(),
                MarketType.UPBIT.getName(),
                LocalDate.now()
        )
                .filter(x -> priceMap.containsKey(x.getTicker()))
                .map(x -> {
                    UpbitWalletCoinPriceDataResponse data = priceMap.get(x.getTicker());
                    return UserAsset.builder()
                            .assetId(x.getAssetId())
                            .userId(x.getUserId())
                            .ticker(data.getCurrency())
                            .market(MarketType.UPBIT.getName())
                            .price(data.getAvg_buy_price())
                            .volume(data.getBalance())
                            .insertDt(LocalDate.now()).build();
                })
                .doOnNext(userAssetRepository::save)
                .doOnEach(System.out::println)
                .doOnError(e -> e.printStackTrace())
                .subscribe(x -> tickers.remove(x.getTicker()));
    }
}
