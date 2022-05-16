package coinbuying.coinbuyingasset.handler;


import coinbuying.coinbuyingasset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AssetHandler {

    private final AssetService assetService;

    public Mono<ServerResponse> findContent(ServerRequest request) {
        //완료
        Mono<Object> response = null;//postService.findContent(request)
                //.subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
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

    public Mono<ServerResponse> downloadFile(ServerRequest request) {

        Mono<Object> response = null;
        /*Mono<Object> response = postService.findContent(request)
                .subscribeOn(Schedulers.boundedElastic());*/

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Object.class);
    }
}
