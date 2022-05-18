package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface UserAssetRepository extends ReactiveCrudRepository<UserAsset, Long> {
    Flux<UserAsset> findByUserIdAndInsertDt(int userId, LocalDate insertDt);
    Flux<UserAsset> findByUserIdAndMarketAndInsertDt(int userId, String market, LocalDate insertDt);
}