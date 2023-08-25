package wo.it.exceptions;

import lombok.Getter;
import wo.it.models.authentication.RegistrationCritic;

import java.util.List;

@Getter
public class InvalidFormularyException extends Exception {

    private List<RegistrationCritic> critics;

    public InvalidFormularyException (String message, List<RegistrationCritic> critics) {
        super(message);
        this.critics = critics;
    }

}
