package coinbuying.coinbuyingasset.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class UserAssetResponse extends SuccessResponse {

    private LocalDate insertDt;
    private List<UserAssetOne> data;

    private UserAssetResponse() {};

    public static UserAssetResponse createUserAssetResponse(LocalDate insertDt, List<UserAssetOne> data){
        UserAssetResponse userAssetResponse = new UserAssetResponse();
        userAssetResponse.insertDt = insertDt;
        userAssetResponse.data = data;
        return userAssetResponse;
    }
}
