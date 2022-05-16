package coinbuying.coinbuyingasset.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface AssetService {
    Mono<Object> findContent(ServerRequest serverRequest);
    Mono<Object> getPostsByBoardType(ServerRequest serverRequest);
}
