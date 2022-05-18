package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.repository.CoinPriceRepository;
import org.springframework.stereotype.Service;

@Service
public class CoinPriceServiceImpl implements CoinPriceService{
    private final CoinPriceRepository coinPriceRepository;

    public CoinPriceServiceImpl(CoinPriceRepository coinPriceRepository) {
        this.coinPriceRepository = coinPriceRepository;
    }
}
