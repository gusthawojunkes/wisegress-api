package wo.it.models.authentication;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CredentialTest {

    @DisplayName("`Credential.getEncryptedPassword()` must return an encrypted password when the input is not null")
    @Test
    void getEncryptedPasswordMethodMustReturnAnEncryptedPasswordWhenTheInputIsNotNull() {
        Credential credential = new Credential("test@test.com", "123");
        String encryptedPassword = credential.getEncryptedPassword();
        assertTrue(StringUtils.isNotBlank(encryptedPassword));
    }

}