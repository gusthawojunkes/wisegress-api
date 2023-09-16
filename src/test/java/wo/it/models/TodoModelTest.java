package wo.it.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.core.enums.Priority;
import wo.it.database.entities.Todo;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoModelTest {

    @DisplayName("`TodoModel.loadFrom()` should fill the model correctly")
    @Test
    void loadFromMethodShouldFillTheModelCorrectly() {
        var insertedAt = LocalDateTime.now();
        var updatedAt = LocalDateTime.now();
        var uuid = UUID.randomUUID().toString();

        var todo = new Todo();
        todo.setInsertedAt(insertedAt);
        todo.setUpdatedAt(updatedAt);
        todo.setUuid(uuid);
        todo.setPriority(Priority.HIGHEST);
        todo.setContent("Todo");
        todo.setDone(true);

        var model = TodoModel.loadFrom(todo);
        assertEquals(Priority.HIGHEST, model.getPriority());
        assertEquals(insertedAt, model.getInsertedAt());
        assertEquals(updatedAt, model.getUpdatedAt());
        assertEquals(uuid, model.getUuid());
        assertEquals("Todo", model.getContent());
        assertTrue(model.isDone());
    }

    @DisplayName("`TodoModel.isDone()` should return false if the property is null")
    @Test
    void isDoneMethodShouldReturnFalseIfThePropertyIsNull() {
        var todo = new TodoModel();
        todo.setDone(false);

        assertFalse(todo.isDone());
    }
}