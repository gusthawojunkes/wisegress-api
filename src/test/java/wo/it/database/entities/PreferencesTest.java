package wo.it.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PreferencesTest {

    @DisplayName("`Preferences.toModel()` should set the correct values into the model")
    @Test
    void toModelMethodShouldSetTheCorrectValuesIntoTheModel() {
        var preferences = new Preferences();
        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();
        preferences.setUuid(uuid);
        preferences.setInsertedAt(now);
        preferences.setUpdatedAt(now);
        preferences.setUseKanban(true);
        preferences.setUseTodo(false);
        preferences.setUsePomodoro(true);
        preferences.setUseAgenda(false);
        preferences.setPomodoroConfiguration(new PomodoroConfiguration());

        var model = preferences.toModel();
        assertNotNull(model);
        assertEquals(uuid, model.getUuid());
        assertEquals(now, model.getInsertedAt());
        assertEquals(now, model.getUpdatedAt());
        assertTrue(model.useKanban());
        assertFalse(model.useTodo());
        assertTrue(model.usePomodoro());
        assertFalse(model.useAgenda());

    }

}