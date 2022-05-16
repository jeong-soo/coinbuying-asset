package coinbuying.coinbuyingasset.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class AssetServiceImpl implements AssetService {

    @Override
    public Mono<Object> findContent(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public Mono<Object> getPostsByBoardType(ServerRequest serverRequest) {
        return null;
    }
}
