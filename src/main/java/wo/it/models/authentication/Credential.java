package wo.it.models.authentication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Credential {

    private String email;
    private String password;

    public static String sanitize(String text) {
        return text;
    }

    public String getSanitizedEmail() {
        return sanitize(this.email);
    }

    public String getSanitizedPassword() {
        return sanitize(this.password);
    }


}
