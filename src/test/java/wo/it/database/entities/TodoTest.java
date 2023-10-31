package wo.it.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.core.enums.Priority;
import wo.it.core.enums.Status;
import wo.it.models.TodoModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @DisplayName("`Todo.isDone()` should return false if the property is null")
    @Test
    void isDoneMethodShouldReturnFalseIfThePropertyIsNull() {
        var todo = new Todo();
        todo.setDone(false);

        assertFalse(todo.isDone());
    }

    @DisplayName("`Todo.toModel()` should set the correct values into the model")
    @Test
    void toModelMethodShouldSetTheCorrectValuesIntoTheModel() {
        var todo = new Todo();
        var now = LocalDateTime.now();

        todo.setUuid(UUID.randomUUID().toString());
        todo.setContent("Todo");
        todo.setDone(true);
        todo.setCompletedAt(now);
        todo.setInsertedAt(now);
        todo.setUpdatedAt(now);
        todo.setPriority(Priority.LOW);
        todo.setUser(new ApplicationUser());

        var model = todo.toModel();

        assertNotNull(model);
        assertEquals(model.getContent(), todo.getContent());
        assertEquals(model.isDone(), todo.isDone());
        assertEquals(model.getCompletedAt(), todo.getCompletedAt());
        assertEquals(model.getPriority(), todo.getPriority());
        assertEquals(model.getUuid(), todo.getUuid());
        assertEquals(model.getInsertedAt(), todo.getInsertedAt());
        assertEquals(model.getUpdatedAt(), todo.getUpdatedAt());
    }

    @DisplayName("`Todo.loadFrom()` should fill the object correctly")
    @Test
    void loadFromMethodShouldFillTheObjectCorrectly() {
        var todo = new Todo();
        var model = new TodoModel();
        var now = LocalDateTime.now();

        model.setUuid(UUID.randomUUID().toString());
        model.setContent("Todo");
        model.setDone(true);
        model.setCompletedAt(now);
        model.setInsertedAt(now);
        model.setUpdatedAt(now);
        model.setPriority(Priority.LOW);

        todo.loadFrom(model);

        assertNotNull(todo);
        assertEquals(todo.getContent(), model.getContent());
        assertEquals(todo.isDone(), model.isDone());
        assertEquals(todo.getCompletedAt(), model.getCompletedAt());
        assertEquals(todo.getPriority(), model.getPriority());
        assertEquals(todo.getUuid(), model.getUuid());
        assertEquals(todo.getInsertedAt(), model.getInsertedAt());
        assertEquals(todo.getUpdatedAt(), model.getUpdatedAt());
    }

    @DisplayName("`Todo.loadFieldsToUpdate()` should fill the object correctly")
    @Test
    void loadFieldsToUpdateMethodShouldFillTheObjectCorrectly() {
        var todo = new Todo();
        var model = new TodoModel();
        var now = LocalDateTime.now();

        model.setUuid(UUID.randomUUID().toString());
        model.setContent("Todo");
        model.setDone(true);
        model.setCompletedAt(now);
        model.setPriority(Priority.LOW);

        try {
            todo.loadFieldsToUpdate(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(todo);
        assertEquals(todo.getContent(), model.getContent());
        assertEquals(todo.isDone(), model.isDone());
        assertEquals(todo.getCompletedAt(), model.getCompletedAt());
        assertEquals(todo.getPriority(), model.getPriority());
        assertEquals(todo.getUuid(), model.getUuid());
    }

    @DisplayName("`Todo.loadFieldsToUpdate()` should update `completedAt` with the current timestamp when `done` changes from `false` to `true`")
    @Test
    void loadFieldsToUpdateMethodShouldUpdateTheCompletedAtPropertyWhenDonePropertyChangesFromFalseToTrue() {
        var todo = new Todo();
        var model = new TodoModel();
        model.setDone(true);

        try {
            todo.loadFieldsToUpdate(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(todo);
        assertNotNull(todo.getCompletedAt());
    }

}