package coinbuying.coinbuyingasset.handler;


import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AssetHandler {

    private final AssetService assetService;

    public Mono<ServerResponse> getWallet(ServerRequest request) {
        /*CREATE TABLE IF NOT EXISTS user_asset (
                asset_id INT(10) AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT 'asset id',
                user_id INT(10) NOT NULL COMMENT 'user id',
                ticker VARCHAR(6) NOT NULL COMMENT 'coin code',
                market VARCHAR(20) NOT NULL COMMENT 'coin market',
                price DOUBLE(20,5) NOT NULL COMMENT 'coin price',
                volume DOUBLE(20,5) NOT NULL COMMENT 'coin volume',
                insert_dttm DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        );*/

        Flux<UserAsset> response = assetService.getWallet(request)//postService.findContent(request)
                        .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserAsset.class);
    }

    public Mono<ServerResponse> postsRegistration(ServerRequest request) {

        Mono<Object> response = null;//postService.postsRegistration(request)
                //.subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }

    public Mono<ServerResponse> postsModify(ServerRequest request) {

        Mono<Object> response = null;//postService.postsModify(request)
               // .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }

    public Mono<ServerResponse> modifyPostsShowYn(ServerRequest request) {

        Mono<Object> response = null; //postService.modifyPostsShowYn(request)
                //.subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }

    public Mono<ServerResponse> getPostsByBoardType(ServerRequest request) {

        Mono<Object> response = null;//postService.getPostsByBoardType(request)
             //   .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }

    public Mono<ServerResponse> getPostsByBoardTypeAndPostType(ServerRequest request) {

        Mono<Object> response = null;//postService.getPostsByBoardTypeAndPostType(request)
                //.subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }

    public Mono<ServerResponse> getFavorites(ServerRequest request) {

        Mono<Object> response = null; //postService.getFavorites(request)
         //       .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }
}
