package wo.it.models.authentication;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class AuthenticationResponse {

    public AuthenticationResponse() {
        this.error = false;
        this.success = false;
        this.message = "";
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

}
