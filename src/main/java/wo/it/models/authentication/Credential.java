package wo.it.models.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {

    @Email @NotBlank(message = "email may not be blank")
    private String email;
    private String password;
        public String getEncryptedPassword() {
            try {
                return Password.encrypt(this.password);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
}
