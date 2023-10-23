package wo.it.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Feature;
import wo.it.core.enums.Rating;
import wo.it.database.entities.Feedback;

@Getter
@Setter
public class FeedbackModel extends AbstractModel {

    @NotNull
    private Feature feature;

    @NotNull
    private Rating rating;

    private String description;

    @NotBlank
    private String userUuid;

    public Feedback toEntity() {
        var entity = new Feedback();
        entity.setUuid(this.getUuid());
        entity.setInsertedAt(this.insertedAt);
        entity.setUpdatedAt(this.updatedAt);
        entity.setDescription(this.description);
        entity.setFeature(this.feature);
        entity.setRating(this.rating);
        return entity;
    }

}
