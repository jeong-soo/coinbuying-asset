package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAssetRepository extends R2dbcRepository<UserAsset, Long> {
    Flux<UserAsset> findByUserId(int userId);
}