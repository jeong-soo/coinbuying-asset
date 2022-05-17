package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.CoinPrice;
import coinbuying.coinbuyingasset.repository.CoinPriceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CoinPriceServiceImpl implements CoinPriceService{
    private final CoinPriceRepository coinPriceRepository;

    public CoinPriceServiceImpl(CoinPriceRepository coinPriceRepository) {
        this.coinPriceRepository = coinPriceRepository;
    }

    public Mono<CoinPrice> getPriceDatas(UserAssetOne assetOne) {
        return coinPriceRepository.findByTickerOrderByDttm(assetOne.getTicker());
    }
}
