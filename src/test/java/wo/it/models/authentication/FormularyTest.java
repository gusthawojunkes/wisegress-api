package wo.it.models.authentication;

import jakarta.validation.constraints.Null;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.database.entities.ApplicationUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormularyTest {

    @DisplayName("`Formulary.hasCritics()` must return true if the critics list is not empty")
    @Test
    void hasCriticsMethodMustReturnTrueIfTheCriticsListIsNotEmpty() {
        var formulary = new Formulary();
        formulary.setCritics(List.of(new RegistrationCritic("email", "Email isn't valid", "12345")));

        assertTrue(formulary.hasCritics());
    }

    @DisplayName("`Formulary.hasCritics()` must return false if the critics list is empty")
    @Test
    void hasCriticsMethodMustReturnFalseIfTheCriticsListIsEmpty() {
        var formulary = new Formulary();
        formulary.setCritics(new ArrayList<>());

        assertFalse(formulary.hasCritics());
    }

    @DisplayName("`Formulary.toNewUser()` must not be null")
    @Test
    void toNewUserMethodMustNotBeNull() {
        var formulary = new Formulary();
        formulary.setEmail("test@test.com");
        formulary.setPassword("123456");
        formulary.setBirthday(LocalDate.now());

        ApplicationUser user = formulary.toNewUser();

        assertNotNull(user);
    }

    @DisplayName("`Formulary.toNewUser()` must be filled correctly")
    @Test
    void toNewUserMethodMustBeFilledCorrectly() {
        var birthday = LocalDate.now();
        var formulary = new Formulary();
        formulary.setEmail("test@test.com");
        formulary.setPassword("123456");
        formulary.setBirthday(birthday);

        ApplicationUser user = formulary.toNewUser();

        assertNotNull(user);
        assertEquals("test@test.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertEquals(birthday, user.getBirthday());
    }

    @DisplayName("`Formulary.validate()` must add a new critic if the email is blank")
    @ParameterizedTest(name = "When `formulary.email` is \"{0}\" then the critic must be added")
    @NullSource
    @ValueSource(strings = {"", "     ", " "})
    void validateMethodMustAddNewCriticIfTheEmailIsBlank(String email) {
        var formulary = new Formulary();
        formulary.setEmail(email);
        formulary.setBirthday(LocalDate.of(2007, 3, 12));
        formulary.validate();

        assertTrue(formulary.hasCritics());
    }
}