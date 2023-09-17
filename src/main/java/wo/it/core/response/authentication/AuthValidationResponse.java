package wo.it.core.response.authentication;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import wo.it.models.ApplicationUserModel;
import wo.it.core.response.CommonValidationResponse;

@Getter
@Setter
public class AuthValidationResponse extends CommonValidationResponse {

    private String token;
    private ApplicationUserModel user;

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
