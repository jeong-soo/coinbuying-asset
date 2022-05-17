package coinbuying.coinbuyingasset.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpbitWalletCoinPriceDataResponse {
    private String currency;
    private Double balance;
    private Double locked;
    private Double avg_buy_price;
    private Boolean avg_buy_price_modified;
    private String unit_currency;
}
