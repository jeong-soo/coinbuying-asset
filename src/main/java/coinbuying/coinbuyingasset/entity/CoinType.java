package coinbuying.coinbuyingasset.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinType {
    BTC("BTC", "Bitcoin", "비트코인"),
    ETH("ETH", "", "이더리움"),
    XRP("XRP", "","리플"),
    EOS("EOS", "","이오스"),
    DOGE("DOGE", "","도지코인");


    private String ticker;
    private String fullName;
    private String fullNameKR;
}
