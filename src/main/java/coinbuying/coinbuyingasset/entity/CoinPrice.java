package coinbuying.coinbuyingasset.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value="coin_price")
@AllArgsConstructor
@Getter
@Builder
public class CoinPrice {
    @Id
    @Column(value="price_id")
    private Long priceId;

    @Column(value="ticker")
    private String ticker;

    @Column(value="market")
    private String market;

    @Column(value="price")
    private Double price;

    @Column(value="dttm")
    private LocalDateTime dttm;
}
