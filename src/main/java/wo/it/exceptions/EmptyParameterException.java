package wo.it.exceptions;

import lombok.Getter;

@Getter
public class EmptyParameterException extends Exception {

    private String parameterName;

    public EmptyParameterException(String message) {
        super(message);
    }

    public EmptyParameterException(String message, String parameterName) {
        super(message);
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName == null ? "" : parameterName;
    }
}
