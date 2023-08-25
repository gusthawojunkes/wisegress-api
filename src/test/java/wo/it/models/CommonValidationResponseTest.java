package wo.it.models;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CommonValidationResponseTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("`CommonValidationResponse.class` with any message must not be valid")
    @ParameterizedTest(name = "With message and success = {0} must not be valid")
    @ValueSource(booleans = { true, false })
    void responseWithMessageMustNotBeValid(boolean success) {
        var response = new CommonValidationResponse();
        response.setSuccess(success);
        response.setMessage("Some error message");

        assertFalse(response.valid());
    }

    @DisplayName("`CommonValidationResponse.hasErrors()` must return the same value of the `error` property")
    @ParameterizedTest(name = "When {0} then return {0}")
    @ValueSource(booleans = { true, false })
    void hasErrorsFunctionMustReturnTheSameValueOfTheErrorProperty(boolean error) {
        var response = new CommonValidationResponse();
        response.setError(error);

        assertEquals(response.hasErrors(), response.isError());
    }

    @DisplayName("`CommonValidationResponse.hasCritics()` must return true when the property `message` is not blank")
    @Test()
    void hasCriticsFunctionMustAlertIfTheMessagePropertyIsNotBlank() {
        var response = new CommonValidationResponse();
        response.setMessage("Has some message");

        assertTrue(response.hasCritics());
    }

    @DisplayName("`CommonValidationResponse.hasCritics()` must return false when the property `message` is blank, success is true and error is false")
    @Test()
    void hasCriticsFunctionMustBeFalseIfHasNoMessageAndHasNoErrorAndSuccessIsTrue() {
        var response = new CommonValidationResponse();
        response.setMessage(null);
        response.setError(false);
        response.setSuccess(true);

        assertFalse(response.hasCritics());
    }

    @DisplayName("Constructor for `CommonValidationResponse.class` with no arguments must initialize with default values")
    @Test
    void noArgsConstructorForCommonValidationResponseMustInitializeWithDefaultValue() {
        var response =  new CommonValidationResponse();

        assertFalse(response.isError());
        assertFalse(response.isSuccess());
        assertEquals(StringUtils.EMPTY, response.getMessage());
    }

    @DisplayName("`CommonValidationResponse.initWithSuccess()` must return an instance with the property `success` = true")
    @Test
    void initWithSuccessMethodMustReturnAnInstanceWithThePropertySuccessEqualsTrue() {
        var response = CommonValidationResponse.initWithSuccess();
        assertTrue(response.isSuccess());
    }

}