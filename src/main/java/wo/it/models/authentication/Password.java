package wo.it.models.authentication;

import org.apache.commons.lang3.StringUtils;
import wo.it.exceptions.EncryptException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Password {

    private Password() {}

    public static String encrypt(String content) throws EncryptException {
        if (StringUtils.isBlank(content)) {
            throw new EncryptException("A senha recebia para encriptação não pode ser vazia!");
        }

        String encryptedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(content.getBytes());
            encryptedPassword = Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(encryptedPassword)) {
            throw new EncryptException("Algo deu errado, o resultado da encriptação foi um valor vazio!");
        }

        return encryptedPassword;
    }
}
