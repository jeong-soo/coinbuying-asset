package coinbuying.coinbuyingasset.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum MarketType {
    BITHUMB("BITHUMB"),
    UPBIT("UPBIT"),
    NONE("NONE");

    private String name;
}
