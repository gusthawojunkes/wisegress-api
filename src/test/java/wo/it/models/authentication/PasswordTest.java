package wo.it.models.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.core.exceptions.EncryptException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @DisplayName("`Password.encrypt()` must throw an exception if the password content is blank")
    @ParameterizedTest(name = "When `password` is \"{0}\" then must throw an exception")
    @NullSource
    @ValueSource(strings = {"", "   ", " "})
    void encryptMethodMustThrowAnExceptionIfThePasswordContentIsBlank(String password) {
        EncryptException exception = assertThrows(EncryptException.class, () -> {
            Password.encrypt(password);
        });

        assertEquals("A senha recebida para encriptação não pode ser vazia!", exception.getMessage());
    }

    @DisplayName("`Password.encrypt()` must not be null")
    @ParameterizedTest(name = "When `password` is \"{0}\" then the result must not be null")
    @ValueSource(strings = {"1234", "aseweqw", "123$@!lL;^^", "$str0NgP4$$"})
    void encryptMethodMustNotBeNull(String password) throws EncryptException {
        String encryptedPassword = Password.encrypt(password);
        assertNotNull(encryptedPassword);
    }

    @DisplayName("`Password.encrypt()` must return a different value then the original password")
    @Test
    void encryptMethodMustReturnADifferentValueThenTheOriginalPassword() throws EncryptException {
        String password  = "2321@!34343mna";
        String encryptedPassword = Password.encrypt(password);
        System.out.println(encryptedPassword);
        assertNotEquals(password, encryptedPassword);
    }

    @DisplayName("`Password.encrypt()` must produces the same result with two different sources with the same content")
    @Test
    void encryptMethodMustProducesTheSameResultWithTwoDifferentSourcesWithTheSameContent() throws EncryptException {
        String firstEncryptedPassword = Password.encrypt("2321@!34343mna");
        String secondEncryptedPassword = Password.encrypt("2321@!34343mna");
        assertEquals(firstEncryptedPassword, secondEncryptedPassword);
    }
}