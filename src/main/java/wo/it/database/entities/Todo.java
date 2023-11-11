package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.enums.Priority;
import wo.it.core.exceptions.UpdateException;
import wo.it.models.TodoModel;

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
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private ApplicationUser user;

    public Todo() {
        this.done = false;
    }

    public TodoModel toModel() {
        var model = new TodoModel();

        model.setUuid(this.getUuid());
        model.setContent(this.getContent());
        model.setDone(this.isDone());
        model.setCompletedAt(this.getCompletedAt());
        model.setInsertedAt(this.getInsertedAt());
        model.setUpdatedAt(this.getUpdatedAt());
        model.setPriority(this.getPriority());

        return model;
    }

    public void loadFrom(TodoModel model) {
        this.setUuid(model.getUuid());
        this.setContent(model.getContent());
        this.setDone(model.isDone());
        this.setCompletedAt(model.getCompletedAt());
        this.setInsertedAt(model.getInsertedAt());
        this.setUpdatedAt(model.getUpdatedAt());
        this.setPriority(model.getPriority());
    }

    public void loadFieldsToUpdate(TodoModel model) throws UpdateException {
        this.setUuid(model.getUuid());
        this.setContent(model.getContent());
        this.setCompletedAt(model.getCompletedAt());
        if (!this.isDone() && model.isDone()) {
            this.setCompletedAt(LocalDateTime.now());
        }
        this.setDone(model.isDone());
        this.setPriority(model.getPriority());
    }

}
