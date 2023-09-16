package wo.it.models;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.models.authentication.AuthValidationResponse;

import static org.junit.jupiter.api.Assertions.*;

class CommonValidationResponseTest {

    @DisplayName("`AuthValidationResponse.class` with any message must not be valid")
    @ParameterizedTest(name = "With message and success = {0} must not be valid")
    @ValueSource(booleans = { true, false })
    void responseWithMessageMustNotBeValid(boolean success) {
        var response = AuthValidationResponse.init();
        response.setSuccess(success);
        response.setMessage("Some error message");

        assertFalse(response.valid());
    }

    @DisplayName("`AuthValidationResponse.hasErrors()` must return the same value of the `error` property")
    @ParameterizedTest(name = "When {0} then return {0}")
    @ValueSource(booleans = { true, false })
    void hasErrorsFunctionMustReturnTheSameValueOfTheErrorProperty(boolean error) {
        var response = AuthValidationResponse.init();
        response.setError(error);

        assertEquals(response.hasErrors(), response.isError());
    }

    @DisplayName("`AuthValidationResponse.hasCritics()` must return true when the property `message` is not blank")
    @Test()
    void hasCriticsFunctionMustAlertIfTheMessagePropertyIsNotBlank() {
        var response = AuthValidationResponse.init();
        response.setMessage("Has some message");

        assertTrue(response.hasCritics());
    }

    @DisplayName("`AuthValidationResponse.hasCritics()` must return false when the property `message` is blank, success is true and error is false")
    @Test()
    void hasCriticsFunctionMustBeFalseIfHasNoMessageAndHasNoErrorAndSuccessIsTrue() {
        var response = AuthValidationResponse.init();
        response.setMessage(null);
        response.setError(false);
        response.setSuccess(true);

        assertFalse(response.hasCritics());
    }

    @DisplayName("`AuthValidationResponse.hasCritics()` must be true if has message or has error or is not valid")
    @Test()
    void hasCriticsFunctionMustBeTrueIfHasMessageOrHasErrorOrIsNotValid() {
        var response = AuthValidationResponse.init();
        response.setMessage(null);
        response.setError(false);
        response.setSuccess(false);

        assertTrue(response.hasCritics());
    }

    @DisplayName("Constructor for `AuthValidationResponse.class` with no arguments must initialize with default values")
    @Test
    void noArgsConstructorForAuthValidationResponseMustInitializeWithDefaultValue() {
        var response = AuthValidationResponse.init();

        assertFalse(response.isError());
        assertFalse(response.isSuccess());
        assertEquals(StringUtils.EMPTY, response.getMessage());
    }

    @DisplayName("`AuthValidationResponse.initWithSuccess()` must return an instance with the property `success` = true")
    @Test
    void initWithSuccessMethodMustReturnAnInstanceWithThePropertySuccessEqualsTrue() {
        var response = AuthValidationResponse.initWithSuccess();
        assertTrue(response.isSuccess());
    }

    @DisplayName("`AuthValidationResponse.makeInvalid()` must set the correct values")
    @Test
    void makeInvalidMethodMustSetTheCorrectValues() {
        var response = AuthValidationResponse.init();
        response.makeInvalid();

        assertNull(response.getUser());
        assertTrue(response.isError());
        assertFalse(response.isSuccess());
    }

    @DisplayName("`AuthValidationResponse.makeValid()` must set the correct values")
    @Test
    void makeValidMethodMustSetTheCorrectValues() {
        var response = AuthValidationResponse.init();
        response.makeValid();

        assertFalse(response.isError());
        assertTrue(response.isSuccess());
        assertTrue(StringUtils.isBlank(response.getMessage()));
    }

}