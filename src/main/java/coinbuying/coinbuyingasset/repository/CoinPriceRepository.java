package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.CoinPrice;
import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.cglib.core.Local;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CoinPriceRepository extends R2dbcRepository<CoinPrice, Long> {
    Flux<CoinPrice> findByTickerInAndDttmBetween(List<String> tickers, LocalDate startDttm, LocalDate endDttm);
}