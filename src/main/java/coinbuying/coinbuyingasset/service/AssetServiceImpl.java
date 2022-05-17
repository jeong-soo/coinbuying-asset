package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.response.UserAssetOne;
import coinbuying.coinbuyingasset.dto.response.UserAssetResponse;

import coinbuying.coinbuyingasset.repository.R2dbcEntityAssetRepository;
import coinbuying.coinbuyingasset.repository.UserAssetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Mono;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
    public String getUpbitWallet(ServerRequest serverRequest) {

        String accessKey = "sZiMJou0evyRRV3GB6Nrtgo1a9fuEU5OnSjBHRqM";//System.getenv("UPBIT_OPEN_API_ACCESS_KEY");
        String secretKey = "898Usfgbf54lJDiVs7nBL3mHxLElrBoZRAPnW7dx";//System.getenv("UPBIT_OPEN_API_SECRET_KEY");
        String serverUrl = "https://api.upbit.com";//System.getenv("UPBIT_OPEN_API_SERVER_URL");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        JSONObject jsonObj = null;
        String retVal = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/v1/accounts");
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            String upbitWalletArray =  EntityUtils.toString(entity, "UTF-8");
            System.out.println(upbitWalletArray);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Object> upbitWalletList = objectMapper.readValue(upbitWalletArray, new TypeReference<>() {});

            for(int idx=0; idx<upbitWalletList.size();idx++){
                Map<String, Object> map = (Map<String,Object>) upbitWalletList.get(idx);
                System.out.println(map.get("currency").toString());
                System.out.println(map.get("balance").toString());
                System.out.println(map.get("locked").toString());
                System.out.println(map.get("avg_buy_price").toString());
                System.out.println(map.get("avg_buy_price_modified").toString());
                System.out.println(map.get("unit_currency").toString());

            }



            retVal = upbitWalletArray;
        } catch (IOException e) {
            e.printStackTrace();
        } 

        return retVal;
    }
}
