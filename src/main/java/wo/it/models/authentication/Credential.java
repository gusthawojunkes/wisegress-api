package wo.it.models.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Credential(
        @Email @NotBlank(message = "email may not be blank")
        String email,
        String password
) {}
