package wo.it.models.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import wo.it.models.ApplicationUserModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponse {

    private boolean success;

    private boolean error;

    private String message;

    private List<RegistrationCritic> critics;

    private ApplicationUserModel user;

    public boolean hasCritics() {
        return CollectionUtils.isNotEmpty(critics) || error;
    }

    public boolean valid() {
        return CollectionUtils.isEmpty(critics) && !error && success;
    }

    public void makeInvalid() {
        this.error = true;
        this.success = false;
    }
}
