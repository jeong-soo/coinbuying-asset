package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.UpbitApiKey;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class MockUpbitApiKeyServiceImpl implements UserApiKeyService<UpbitApiKey>{
    @Override
    public UpbitApiKey getKey() {
        return new UpbitApiKey("123123123", "123123123");
    }
}
