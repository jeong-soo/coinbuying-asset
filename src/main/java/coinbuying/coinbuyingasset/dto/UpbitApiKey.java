package coinbuying.coinbuyingasset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpbitApiKey implements UserApiKeyable{
    private String accessKey;
    private String secretKey;
}
