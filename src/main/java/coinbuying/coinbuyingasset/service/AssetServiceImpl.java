package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class AssetServiceImpl implements AssetService {

    @Autowired
    UserAssetRepository userAssetRepository;

    @Override
    public Mono<UserAsset> getWallet(ServerRequest serverRequest) {
        int userId = Integer.parseInt(serverRequest.pathVariable("userId"));

        return userAssetRepository.findByUserId(userId);
    }

    @Override
    public Mono<Object> getPostsByBoardType(ServerRequest serverRequest) {
        return null;
    }
}
