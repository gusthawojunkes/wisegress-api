package wo.it.core.filter;

import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Priority;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskFilter {

    private Boolean done;
    private LocalDateTime completedAt;
    private Priority priority;
    private String user;

    private TaskFilter() {}

    public static TaskFilter from(String user) {
        var filter = new TaskFilter();
        filter.setUser(user);
        return filter;
    }

    public TaskFilter unfinished() {
        this.setDone(false);
        return this;
    }

}
