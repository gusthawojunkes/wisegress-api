package wo.it.core.filter;

import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Priority;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoFilter {

    private Boolean done;
    private LocalDateTime completedAt;
    private Priority priority;
    private String user;

    private TodoFilter() {}

    public static TodoFilter from(String user) {
        var filter = new TodoFilter();
        filter.setUser(user);
        return filter;
    }

    public TodoFilter unfinished() {
        this.setDone(false);
        return this;
    }

}
