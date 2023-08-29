package wo.it.models;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import wo.it.database.models.ApplicationUser;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ApplicationUserModelTest {

    @Test
    void loadFromMethodMustFillTheModelCorrectly() {
        var user = new ApplicationUser();
        user.setName("Gusthawo Junkes");
        user.setEmail("teste@teste.com");
        user.setPassword("1234");
        user.setStatus(Status.ACTIVE);


        var model = ApplicationUserModel.loadFrom(user);

        assertEquals("Gusthawo Junkes", model.getName());
        assertEquals("teste@teste.com", model.getEmail());
        assertEquals("1234", model.getPassword());
        assertEquals(Status.ACTIVE, model.getStatus());
    }
}