package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserAssetRepository extends R2dbcRepository<UserAsset, Long> {
    Flux<UserAsset> findByUserIdAndInsertDt(int userId, LocalDate insertDt);

//    Flux<UserAsset> findByUserIdAndMarketAndInsertDtAndTickerInOrderByTicker(int userId, String market, LocalDate insertDt, List<String> tickers);

    Flux<UserAsset> findByUserIdAndMarketAndInsertDt(int userId, String market, LocalDate insertDt);
//    Flux<UserAsset> findByUserIdAndInsertDttmBetween(int userId, LocalDate startInsertDt, LocalDate endInsertDt);

    //select * from asset_manage.user_asset
    //where insert_dttm = (SELECT max(insert_dttm) frOM asset_manage.user_asset where user_id = 0);
}