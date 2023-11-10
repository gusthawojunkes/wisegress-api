package wo.it.models;

import lombok.Getter;
import lombok.Setter;
import wo.it.database.entities.Event;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventModel extends AbstractModel {

    private LocalDateTime date;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    public Event toEntity() {
        Event event = new Event();
        event.setUuid(this.getUuid());
        event.setDate(this.getDate());
        event.setDescription(this.getDescription());
        event.setStartDate(this.getStartDate());
        event.setEndDate(this.getEndDate());
        return event;
    }
}
