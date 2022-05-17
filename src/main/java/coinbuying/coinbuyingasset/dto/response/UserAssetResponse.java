package coinbuying.coinbuyingasset.dto.response;

import coinbuying.coinbuyingasset.entity.UserAsset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAssetResponse extends SuccessResponse {

    private LocalDate insertDt;
    private List<UserAssetOne> data;

    public static UserAssetResponse CreateUserAssetResponse (LocalDate insertDt, List<UserAssetOne> data){
        UserAssetResponse userAssetResponse = new UserAssetResponse(insertDt, data);
        return userAssetResponse;
    }
}
