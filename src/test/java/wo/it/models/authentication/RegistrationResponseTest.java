package wo.it.models.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationResponseTest {

    @DisplayName("`RegistrationResponse.hasCritics()` must return true if the error property is true")
    @Test
    void hasCriticsMethodMustReturnTrueIfErrorIsTrue() {
        var response = new RegistrationResponse();
        response.setError(true);

        assertTrue(response.hasCritics());
    }

    @DisplayName("`RegistrationResponse.hasCritics()` must return true if the critic list is not empty")
    @Test
    void hasCriticsMethodMustReturnTrueIfTheCriticListIsNotEmpty() {
        var response = new RegistrationResponse();
        response.setError(false);
        response.setCritics(List.of(new RegistrationCritic("email", "Email isn't valid", "abc")));

        assertTrue(response.hasCritics());
    }

    @DisplayName("`RegistrationResponse.makeInvalid()` must set the correct values")
    @Test
    void makeInvalidMethodMustSetTheCorrectValues() {
        var response = new RegistrationResponse();
        response.makeInvalid();

        assertTrue(response.isError());
        assertFalse(response.isSuccess());
    }

    @DisplayName("`RegistrationResponse.valid()` must return true if the properties are correct")
    @Test
    void makeInvalidMethodMustReturnTrueIfThePropertiesAreCorrect() {
        var response = new RegistrationResponse();
        response.setError(false);
        response.setSuccess(true);
        response.setCritics(new ArrayList<>());

        assertTrue(response.valid());
    }
}