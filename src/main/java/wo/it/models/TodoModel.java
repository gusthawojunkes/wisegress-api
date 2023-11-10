package wo.it.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import wo.it.core.enums.Priority;
import wo.it.database.entities.Todo;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoModel extends AbstractModel {

    @NotBlank
    private String content;

    @NotNull
    private boolean done;

    @Null
    private LocalDateTime completedAt;

    private Priority priority = Priority.MEDIUM;

    public static TodoModel loadFrom(Todo todo) {
        TodoModel model = new TodoModel();
        model.setUuid(todo.getUuid());
        model.setInsertedAt(todo.getInsertedAt());
        model.setUpdatedAt(todo.getUpdatedAt());
        model.setContent(todo.getContent());
        model.setDone(todo.isDone());
        model.setPriority(todo.getPriority());
        if (todo.getUser() != null) {
            model.setUserUuid(todo.getUser().getUuid());
        }
        return model;
    }

    public Todo toEntity() {
        var todo = new Todo();
        todo.setUuid(this.uuid);
        todo.setInsertedAt(this.insertedAt);
        todo.setUpdatedAt(this.updatedAt);
        todo.setContent(this.content);
        todo.setDone(this.done);
        todo.setPriority(this.priority);
        todo.setCompletedAt(this.completedAt);

        return todo;
    }
}
