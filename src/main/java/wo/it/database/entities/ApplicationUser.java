package wo.it.database.entities;

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

    @Column(length = 60, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate birthday;

    private Status status;

    public boolean isBlocked() {
        return this.status == Status.BLOCKED;
    }

}
