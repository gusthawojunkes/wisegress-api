package wo.it.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmptyParameterExceptionTest {

    @DisplayName("`EmptyParameterException.getParameterName()` must return a not null value")
    @ParameterizedTest(name = "When the argument is \"{0}\" then the returned value must not be null")
    @NullSource
    @ValueSource(strings = {"email", "password"})
    void getParameterNameMustReturnANotNullValue(String parameterName) {
        var exception = new EmptyParameterException("Um erro ocorreu", parameterName);

        assertNotNull(exception.getParameterName());
    }
}