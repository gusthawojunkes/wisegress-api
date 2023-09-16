package wo.it.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.core.enums.Priority;
import wo.it.core.enums.Status;

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

}