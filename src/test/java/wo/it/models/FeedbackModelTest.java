package wo.it.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.core.enums.Feature;
import wo.it.core.enums.Rating;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FeedbackModelTest {

    @DisplayName("`ApplicationUser.toModel()` should set the correct values into the model")
    @Test
    void toModelMethodShouldSetTheCorrectValuesIntoTheModel() {
        var model = new FeedbackModel();
        var now = LocalDateTime.now();

        model.setUuid(UUID.randomUUID().toString());
        model.setInsertedAt(now);
        model.setUpdatedAt(now);
        model.setRating(Rating.GOOD);
        model.setFeature(Feature.KANBAN);
        model.setDescription("Test");

        var feedback = model.toEntity();

        assertNotNull(model);
        assertEquals(feedback.getUuid(), model.getUuid());
        assertEquals(feedback.getInsertedAt(), model.getInsertedAt());
        assertEquals(feedback.getUpdatedAt(), model.getUpdatedAt());
        assertEquals(feedback.getFeature(), model.getFeature());
        assertEquals(feedback.getRating(), model.getRating());
        assertEquals(feedback.getDescription(), model.getDescription());
    }

}