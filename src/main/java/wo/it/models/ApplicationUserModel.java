package wo.it.models;

import lombok.Getter;
import lombok.Setter;
import wo.it.database.entities.ApplicationUser;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationUserModel {
    private String name;
    private String email;
    private String password;
    private String uuid;
    private LocalDate birthday;
    private Status status;
    private LocalDateTime insertedAt;
    private LocalDateTime updatedAt;
    private String token;

    public static ApplicationUserModel loadFrom(ApplicationUser user) {
        var model = new ApplicationUserModel();
        model.setName(user.getName());
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        model.setUuid(user.getUuid());
        model.setStatus(user.getStatus());
        model.setBirthday(user.getBirthday());
        model.setInsertedAt(user.getInsertedAt());
        model.setUpdatedAt(user.getUpdatedAt());

        return model;
    }
}
