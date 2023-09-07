package wo.it.models.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Credential(
        @Email @NotBlank(message = "email may not be blank")
        String email,
        @NotBlank @Size(min = 4)
        String password
) {}
