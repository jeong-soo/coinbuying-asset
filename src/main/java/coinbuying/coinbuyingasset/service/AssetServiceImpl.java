package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;

import coinbuying.coinbuyingasset.repository.R2dbcEntityAssetRepository;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Mono;


import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    @Autowired
    UserAssetRepository userAssetRepository;

    @Autowired
    R2dbcEntityAssetRepository r2dbcAssetRepo;

    @Override
    public Mono<UserAssetResponse> getWallet(ServerRequest serverRequest) {
        int userId = Integer.parseInt(serverRequest.pathVariable("userId"));

         return userAssetRepository.findByUserIdAndInsertDttm(userId, LocalDate.now())
                 .flatMap(asset -> {
                     double total = asset.getPrice() * asset.getVolume();
                     return Mono.just(new UserAssetOne(asset.getAssetId(), asset.getTicker(), asset.getMarket(), asset.getPrice(), asset.getVolume(), total));
                 }).collectList()
                 .map(p->{
                     return UserAssetResponse.CreateUserAssetResponse(LocalDate.now(), p);
                 });
    }

    @Override
    public Mono<Object> getPostsByBoardType(ServerRequest serverRequest) {
        return null;
    }
}
