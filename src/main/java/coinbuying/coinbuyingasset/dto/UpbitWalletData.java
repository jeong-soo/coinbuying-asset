package coinbuying.coinbuyingasset.dto;

import coinbuying.coinbuyingasset.dto.response.UpbitWalletCoinPriceDataResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpbitWalletData implements ResponseWalletData {
    private Integer userId;
    private UpbitWalletCoinPriceDataResponse[] priceDatas;
}
