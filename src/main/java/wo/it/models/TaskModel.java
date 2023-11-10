package wo.it.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Priority;
import wo.it.core.enums.Situation;
import wo.it.database.entities.Task;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskModel extends AbstractModel {

    @NotBlank
    private String description;

    @NotNull
    private Situation situation;

    private boolean done;

    private LocalDateTime completedAt;

    private LocalDateTime dueDate;

    @NotNull
    private Priority priority;

    public Task toEntity() {
        Task task = new Task();
        task.setUuid(this.getUuid());
        task.setInsertedAt(this.getInsertedAt());
        task.setUpdatedAt(this.getUpdatedAt());
        task.setDescription(this.getDescription());
        task.setSituation(this.getSituation());
        task.setDone(this.isDone());
        task.setCompletedAt(this.getCompletedAt());
        task.setDueDate(this.getDueDate());
        task.setPriority(this.getPriority());
        return task;
    }
}
