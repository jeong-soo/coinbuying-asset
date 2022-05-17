package coinbuying.coinbuyingasset.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpbitWalletData {
    private Integer userId;
    private UpbitWalletCoinPriceDataResponse[] priceDatas;
}
