package coinbuying.coinbuyingasset.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class UserAssetOne {

    private Long assetId;

    private String ticker;

    private String market;

    private Double price;

    private Double volume;

    private Double total;

}
