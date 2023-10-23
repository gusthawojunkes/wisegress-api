package wo.it.core.response;

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

    protected boolean success;

    protected boolean error;

    protected String message;

    public boolean hasErrors() {
        return error;
    }

    public boolean valid() {
        return success && StringUtils.isBlank(message);
    }

    public boolean hasCritics() {
        return StringUtils.isNotBlank(message) || hasErrors() || !valid();
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

    public void makeValid() {
        this.error = false;
        this.success = true;
        this.message = StringUtils.EMPTY;
    }

    public void setErrorMessage(String message) {
        this.makeValid();
        this.setMessage(message);
    }

}
