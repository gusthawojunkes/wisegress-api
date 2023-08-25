package wo.it.models.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import wo.it.database.models.ApplicationUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class Formulary {

    private String email;
    private String password;

    private LocalDate birthday;
    private List<RegistrationCritic> critics;

    public Formulary(String email, String password) {
        this.email = email;
        this.password = password;
        this.critics = new ArrayList<>();
    }

    public void validate() {
        throw new NotImplementedException();
    }

    public boolean valid() {
        return CollectionUtils.isEmpty(this.critics);

    }

    public ApplicationUser toNewUser() {
        var user = new ApplicationUser();
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setBirthday(this.getBirthday());
        return user;
    }
}

