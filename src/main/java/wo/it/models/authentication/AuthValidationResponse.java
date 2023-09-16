package wo.it.models.authentication;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import wo.it.models.CommonValidationResponse;

@Getter
@Setter
public class AuthValidationResponse extends CommonValidationResponse {

    private String token;

    private AuthValidationResponse() {
        this.error = false;
        this.success = false;
        this.message = StringUtils.EMPTY;
        this.token = StringUtils.EMPTY;
    }

    public static AuthValidationResponse init() {
        return new AuthValidationResponse();
    }

}
