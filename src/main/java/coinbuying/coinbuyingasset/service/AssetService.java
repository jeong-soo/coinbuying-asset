package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.entity.UserAsset;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetService {
    Flux<UserAsset> getWallet(ServerRequest serverRequest);
    Mono<Object> getPostsByBoardType(ServerRequest serverRequest);
}
