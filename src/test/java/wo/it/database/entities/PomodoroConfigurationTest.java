package wo.it.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PomodoroConfigurationTest {

    @DisplayName("`PomodoroConfiguration.toModel()` should set the correct values into the model")
    @Test
    void toModelMethodShouldSetTheCorrectValuesIntoTheModel() {
        var pomodoroConfiguration = new PomodoroConfiguration();
        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();
        pomodoroConfiguration.setUuid(uuid);
        pomodoroConfiguration.setInsertedAt(now);
        pomodoroConfiguration.setUpdatedAt(now);
        pomodoroConfiguration.setDuration(23);
        pomodoroConfiguration.setShortbreakDuration(4);
        pomodoroConfiguration.setLongbreakDuration(10);
        pomodoroConfiguration.setCicles(4);

        var model = pomodoroConfiguration.toModel();
        assertNotNull(model);
        assertEquals(uuid, model.getUuid());
        assertEquals(now, model.getInsertedAt());
        assertEquals(now, model.getUpdatedAt());
        assertEquals(23, model.getDuration());
        assertEquals(4, model.getShortbreakDuration());
        assertEquals(10, model.getLongbreakDuration());
        assertEquals(4, model.getCicles());
    }

    @DisplayName("`new PomodoroConfiguration()` should initialize with the correct values")
    @Test
    void pomodoroConfigurationConstructorShouldInitializeWithTheCorrectValues() {
        var pomodoroConfiguration = new PomodoroConfiguration();

        assertEquals(25, pomodoroConfiguration.getDuration());
        assertEquals(5, pomodoroConfiguration.getShortbreakDuration());
        assertEquals(15, pomodoroConfiguration.getLongbreakDuration());
        assertEquals(3, pomodoroConfiguration.getCicles());
    }
}