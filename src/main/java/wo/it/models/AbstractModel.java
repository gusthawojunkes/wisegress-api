package wo.it.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractModel {

    protected String uuid;
    protected LocalDateTime insertedAt;
    protected LocalDateTime updatedAt;
    @NotBlank
    protected String userUuid;

}
