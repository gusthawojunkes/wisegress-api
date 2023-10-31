package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.sql.Update;
import wo.it.core.enums.Priority;
import wo.it.core.enums.Situation;
import wo.it.core.exceptions.UpdateException;
import wo.it.models.TaskModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Task extends AbstractEntity {
    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Situation situation;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean done;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private ApplicationUser user;

    public TaskModel toModel() {
        TaskModel model = new TaskModel();
        model.setUuid(this.getUuid());
        model.setInsertedAt(this.getInsertedAt());
        model.setUpdatedAt(this.getUpdatedAt());
        model.setDescription(this.getDescription());
        model.setSituation(this.getSituation());
        model.setDone(this.isDone());
        model.setPriority(this.getPriority());
        model.setCompletedAt(this.getCompletedAt());
        model.setDueDate(this.getDueDate());
        if (this.getUser() != null) {
            model.setUserUuid(this.getUser().getUuid());
        }

        return model;
    }

    public void loadFieldsToUpdate(TaskModel model) throws UpdateException {
        this.setUuid(model.getUuid());
        this.setDescription(model.getDescription());
        this.setCompletedAt(model.getCompletedAt());
        if (!this.isDone() && model.isDone()) {
            this.setCompletedAt(LocalDateTime.now());
        }
        this.setDone(model.isDone());
        this.setPriority(model.getPriority());
        this.setSituation(model.getSituation());
        this.setDueDate(model.getDueDate());
        if (StringUtils.equals(this.getUser().getUuid(), model.getUserUuid())) {
            throw new UpdateException("O UUID da model e do entidade não são os mesmos! (Entidade: " + this.getUser().getUuid() + " / Model: " + model.getUserUuid() + ")");
        }
    }
}
