package coinbuying.coinbuyingasset.repository;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserAssetRepository extends R2dbcRepository<UserAsset, Long> {

}