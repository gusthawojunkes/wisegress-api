package wo.it.core.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventFilter {

    private LocalDateTime date;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private String user;

    private EventFilter() {}

    public static EventFilter from(String user) {
        var filter = new EventFilter();
        filter.setUser(user);
        return filter;
    }
}
