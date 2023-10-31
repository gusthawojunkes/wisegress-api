package wo.it.models;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.core.enums.Status;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.PomodoroConfiguration;
import wo.it.database.entities.Preferences;
import wo.it.database.entities.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ApplicationUserModelTest {

    @DisplayName("`ApplicationUserModel.loadFrom()` should fill the model correctly")
    @Test
    void loadFromMethodMustFillTheModelCorrectly() {
        Preferences preferences = new Preferences();
        preferences.setPomodoroConfiguration(new PomodoroConfiguration());

        var insertedAt = LocalDateTime.now();
        var updatedAt = LocalDateTime.now();
        var birthday = LocalDate.now();
        var uuid = UUID.randomUUID().toString();

        var user = new ApplicationUser();
        user.setName("Gusthawo Junkes");
        user.setEmail("teste@teste.com");
        user.setPassword("1234");
        user.setStatus(Status.ACTIVE);
        user.setInsertedAt(insertedAt);
        user.setUpdatedAt(updatedAt);
        user.setBirthday(birthday);
        user.setUuid(uuid);
        user.setPreferences(preferences);

        List<Todo> todos = List.of(new Todo());
        user.setTodos(todos);

        var model = ApplicationUserModel.loadFrom(user);

        assertEquals("Gusthawo Junkes", model.getName());
        assertEquals("teste@teste.com", model.getEmail());
        assertEquals("1234", model.getPassword());
        assertEquals(Status.ACTIVE, model.getStatus());
        assertEquals(insertedAt, model.getInsertedAt());
        assertEquals(updatedAt, model.getUpdatedAt());
        assertEquals(uuid, model.getUuid());
        assertEquals(birthday, model.getBirthday());
        assertNotNull(model.getTodos());
        assertEquals(1, CollectionUtils.size(model.getTodos()));
    }
}