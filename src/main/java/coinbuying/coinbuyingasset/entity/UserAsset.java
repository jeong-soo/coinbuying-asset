package coinbuying.coinbuyingasset.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value="user_asset")
@AllArgsConstructor
@Getter
@Builder
public class UserAsset {
    @Id
    @Column(value="asset_id")
    private Long assetId;

    @Column(value="user_id")
    private String userId;

    @Column(value="ticker")
    private String ticker;

    @Column(value="market")
    private String market;

    @Column(value="price")
    private Double price;

    @Column(value="volume")
    private Double volume;

    @Column(value="insert_dttm")
    private LocalDateTime insertDttm;
}