package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Priority;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Todo extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean done;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;

    @ManyToOne
    @JoinColumn(name = "applicationuser_uuid", referencedColumnName = "uuid")
    private ApplicationUser user;

    public Todo() {
        this.done = false;
    }

}
