package coinbuying.coinbuyingasset.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class SuccessResponse {

    private final int rt = HttpStatus.OK.value();

    private final String rtMsg = HttpStatus.OK.getReasonPhrase();
}
