package coinbuying.coinbuyingasset.handler;


import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;
import coinbuying.coinbuyingasset.entity.UserAsset;
import coinbuying.coinbuyingasset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AssetHandler {

    private final AssetService assetService;

    public Mono<ServerResponse> getWallet(ServerRequest request) {

        Mono<UserAssetResponse> response = assetService.getWallet(request)//postService.findContent(request)
                        .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserAsset.class);
    }


    public Mono<ServerResponse> getUpbitWallet(ServerRequest request){

        String response = assetService.getUpbitWallet(request);
        //JSONObject personalWallet = assetService.getUpbitWallet(request);

        return ok()
                //.contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.ALL)
                .body(response, String.class);
    }
}
