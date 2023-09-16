package wo.it.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @DisplayName("`Todo.isDone()` should return false if the property is null")
    @Test
    void isDoneMethodShouldReturnFalseIfThePropertyIsNull() {
        var todo = new Todo();
        todo.setDone(false);

        assertFalse(todo.isDone());
    }

}