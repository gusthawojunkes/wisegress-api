package wo.it.core.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends Exception {

    private String message;

    public EntityNotFoundException(String message) {
        super(message);
    }

}
