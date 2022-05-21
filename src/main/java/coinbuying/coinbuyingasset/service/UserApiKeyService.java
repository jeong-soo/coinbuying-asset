package coinbuying.coinbuyingasset.service;

import coinbuying.coinbuyingasset.dto.UserApiKeyable;

public interface UserApiKeyService<T extends UserApiKeyable> {
    T getKey();
}
