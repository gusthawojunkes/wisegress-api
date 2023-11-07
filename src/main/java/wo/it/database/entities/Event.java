package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import wo.it.models.EventModel;
import wo.it.models.TodoModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Event extends AbstractEntity {

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private ApplicationUser user;

    public void loadFieldsToUpdate(EventModel model) {
    }

    public EventModel toModel() {
        var model = new EventModel();
        model.setUuid(this.getUuid());
        model.setDate(this.getDate());
        model.setDescription(this.getDescription());
        model.setStartDate(this.getStartDate());
        model.setEndDate(this.getEndDate());
        model.setInsertedAt(this.getInsertedAt());
        model.setUpdatedAt(this.getUpdatedAt());
        return model;
    }
}
