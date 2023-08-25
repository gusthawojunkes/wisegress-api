package wo.it.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class CommonValidationResponse {

    public CommonValidationResponse() {
        this.error = false;
        this.success = false;
        this.message = StringUtils.EMPTY;
    }

    private boolean success;

    private boolean error;

    private String message;

    public boolean hasErrors() {
        return error;
    }

    public boolean valid() {
        return success && StringUtils.isBlank(message);
    }

    public boolean hasCritics() {
        return StringUtils.isNotBlank(message) && (hasErrors() || !valid());
    }

    public static CommonValidationResponse initWithSuccess() {
        var response = new CommonValidationResponse();
        response.setSuccess(true);
        return response;
    }

    public void makeInvalid() {
        this.error = true;
        this.success = false;
    }

}
