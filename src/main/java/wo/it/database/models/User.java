package wo.it.database.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wo.it.models.Status;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class User extends PanacheEntity {

    public String name;
    public String email;
    public String password;
    public String uuid;
    public LocalDate birthday;
    public Status status;

    public boolean isBlocked() {
        return this.status == Status.BLOCKED;
    }

}
