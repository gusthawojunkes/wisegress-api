package wo.it.models;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import wo.it.database.entities.ApplicationUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ApplicationUserModelTest {

    @Test
    void loadFromMethodMustFillTheModelCorrectly() {
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

        var model = ApplicationUserModel.loadFrom(user);

        assertEquals("Gusthawo Junkes", model.getName());
        assertEquals("teste@teste.com", model.getEmail());
        assertEquals("1234", model.getPassword());
        assertEquals(Status.ACTIVE, model.getStatus());
        assertEquals(insertedAt, model.getInsertedAt());
        assertEquals(updatedAt, model.getUpdatedAt());
        assertEquals(uuid, model.getUuid());
        assertEquals(birthday, model.getBirthday());
        assertNull(model.getToken());
    }
}