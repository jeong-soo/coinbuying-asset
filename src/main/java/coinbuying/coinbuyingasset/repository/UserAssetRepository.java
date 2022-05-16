package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserAssetRepository extends R2dbcRepository<UserAsset, Long> {
    Flux<UserAsset> findByUserIdAndInsertDttm(int userId, LocalDate insertDt);

    //select * from asset_manage.user_asset
    //where insert_dttm = (SELECT max(insert_dttm) frOM asset_manage.user_asset where user_id = 0);
}