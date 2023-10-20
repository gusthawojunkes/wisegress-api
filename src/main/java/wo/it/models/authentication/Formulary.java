package wo.it.models.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import wo.it.database.entities.ApplicationUser;
import wo.it.core.enums.Status;
import wo.it.database.entities.PomodoroConfiguration;
import wo.it.database.entities.Preferences;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Formulary {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthday;
    private List<RegistrationCritic> critics;

    public Formulary(String email, String password) {
        this.email = email;
        this.password = password;
        this.critics = new ArrayList<>();
    }

    public void validate() {
        List<RegistrationCritic> critics = new ArrayList<>();
        if (StringUtils.isBlank(this.email)) {
            critics.add(new RegistrationCritic("email","O e-mail não pode ser vazio", this.email));
        }

        if (this.birthday.isAfter(LocalDate.now())) {
            critics.add(new RegistrationCritic("birthday","A data de aniversário não pode ser maior que a data de hoje!", this.email));
        }

        this.setCritics(critics);
    }

    public boolean hasCritics() {
        return CollectionUtils.isNotEmpty(this.critics);
    }

    public ApplicationUser toNewUser() {
        var user = new ApplicationUser();
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setBirthday(this.getBirthday());
        user.setName(this.getName());
        user.setStatus(Status.ACTIVE);
        var preferences = new Preferences();
        preferences.setPomodoroConfiguration(new PomodoroConfiguration());
        user.setPreferences(preferences);
        return user;
    }

    public List<RegistrationCritic> getCritics() {
        return this.critics == null ? new ArrayList<>() : this.critics;
    }
}

