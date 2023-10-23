package wo.it.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Feature;
import wo.it.core.enums.Rating;

@Getter
@Setter
@Entity
public class Feedback extends AbstractEntity {

    private Feature feature;

    private Rating rating;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private ApplicationUser user;

}