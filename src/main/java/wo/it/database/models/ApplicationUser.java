package wo.it.database.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wo.it.models.Status;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ApplicationUser extends AbstractEntity {

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDate birthday;

    private Status status;

    public boolean isBlocked() {
        return this.status == Status.BLOCKED;
    }

}
