package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserAssetRepository extends ReactiveCrudRepository<UserAsset, Long> {
    Flux<UserAsset> findByUserIdAndMarket(int userId, String market);
}