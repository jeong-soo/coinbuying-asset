package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.UpbitWalletData;
import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AssetServiceTest {
    @Autowired
    AssetService<UpbitWalletData> upbitWalletDataAssetService;

    @Test
    @DisplayName("Upbit 자산 가져오기, db 입력을 제외한 업비트 Flow 테스트")
    void UpbitFlowTestExceptGetWalletAndDbSaveWithMockData() {
        Integer userId = 123;
        UpbitWalletData data = getUpbitMockData(userId);

        Mono<UserAssetResponse> responseMono = Mono.just(data)
                .map(upbitWalletDataAssetService::realTimePriceInjection)
                .flatMap(upbitWalletDataAssetService::fluxCollectToMonoList)
                .map(upbitWalletDataAssetService::UserResponseAssetDataFilter)
                .map(upbitWalletDataAssetService::userAssetsToUserAssetResponse)
                .log();


        // TODO : 검증 데이터 추가 및 검증 로직 추가 필요.
        UserAssetResponse testResult = UserAssetResponse.createUserAssetResponse(LocalDate.now(), new ArrayList<>());
        StepVerifier.create(responseMono).expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }



    UpbitWalletData getUpbitMockData(Integer userId) {
        UpbitWalletData upbitWalletData = new UpbitWalletData();
        upbitWalletData.setUserId(userId);

        List<UpbitWalletCoinPriceDataResponse> list = new ArrayList<>();

        String[] currencys = new String[] {     "BTC",      "ETH",      "USDT",     "WIN",      "SGB",              "BTT",              "APENFT",       "LINK",             "XRP"};
        Double[] balances = new Double[] {      0.13,       2.5,        11.1,       1241.1,     3434.0,             0.001231201,        1231231231.1,   Double.MAX_VALUE,   Double.MIN_VALUE};
        Double[] lockeds = new Double[] {       0.0,        0.0,        0.0,        0.0,        0.0,                0.0,                0.0,            0.0,                0.0};
        Double[] avgBuyPrices = new Double[] {  123123.1,   12414.11,   0.0,        -1.4,       Double.MAX_VALUE,   Double.MIN_VALUE,   141.1,          123.1,              123.1};
        for(int i = 0; i < currencys.length; i++) {
            UpbitWalletCoinPriceDataResponse data = new UpbitWalletCoinPriceDataResponse();
            data.setCurrency(currencys[i]);
            data.setBalance(balances[i]);
            data.setLocked(lockeds[i]);
            data.setAvg_buy_price(avgBuyPrices[i]);
            data.setAvg_buy_price_modified(false);
            data.setUnit_currency("KRW");
            list.add(data);
        }

        upbitWalletData.setPriceDatas(list.toArray(new UpbitWalletCoinPriceDataResponse[list.size()]));
        return upbitWalletData;
    }
}