package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wo.it.models.ApplicationUserModel;
import wo.it.core.enums.Status;

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

    public ApplicationUserModel toModel() {
        var model = new ApplicationUserModel();

        model.setName(this.getName());
        model.setEmail(this.getEmail());
        model.setPassword(this.getPassword());
        model.setBirthday(this.getBirthday());
        model.setUuid(this.getUuid());
        model.setStatus(this.getStatus());
        model.setInsertedAt(this.getInsertedAt());
        model.setUpdatedAt(this.getUpdatedAt());

        return model;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", this.getStatus().getDescription(), this.getName(), this.getEmail());
    }
}
