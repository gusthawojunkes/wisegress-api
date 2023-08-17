package wo.it.models.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponse {

    private boolean success;

    private boolean error;

    private List<String> critics;

    public boolean hasCritics() {
        return CollectionUtils.isNotEmpty(critics) || error;
    }

    public boolean valid() {
        return CollectionUtils.isEmpty(critics) && !error && success;
    }
}
