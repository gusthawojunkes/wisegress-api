package wo.it.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import wo.it.core.enums.Status;
import wo.it.models.TodoModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationUserTest {

    @DisplayName("`ApplicationUser.isBlocked()` should return true when the user has a `Status.BLOCKED` on the status property")
    @Test
    void isBlockedMethodMustReturnTrueWhenTheUserHasBlockedStatus() {
        var user = new ApplicationUser();
        user.setStatus(Status.BLOCKED);

        assertTrue(user.isBlocked());
    }

    @DisplayName("`ApplicationUser.isBlocked()` should return the correct values based on the data")
    @ParameterizedTest(name = "When the status is \"{0}\" then the expected result is \"{1}\"")
    @MethodSource("userStatusIsBlockedExpectedResults")
    void isBlockedMethodMustReturnTheCorrectValuesBasedOnTheData(Status status, boolean expectedResult) {
        var user = new ApplicationUser();
        user.setStatus(status);

        assertEquals(user.isBlocked(), expectedResult);
    }

    @DisplayName("`ApplicationUser.toModel()` should set the correct values into the model")
    @Test
    void toModelMethodShouldSetTheCorrectValuesIntoTheModel() {
        var user = new ApplicationUser();
        var now = LocalDateTime.now();
        List<Todo> todos = List.of(new Todo(), new Todo(), new Todo());

        user.setName("Gusthawo");
        user.setEmail("test@test.com");
        user.setPassword("12345");
        user.setBirthday(LocalDate.of(2002, 10, 17));
        user.setUuid(UUID.randomUUID().toString());
        user.setStatus(Status.ACTIVE);
        user.setInsertedAt(now);
        user.setUpdatedAt(now);
        user.setTodos(todos);
        var preferences = new Preferences();
        preferences.setPomodoroConfiguration(new PomodoroConfiguration());
        user.setPreferences(preferences);

        var model = user.toModel();

        assertNotNull(model);
        assertEquals(model.getName(), user.getName());
        assertEquals(model.getEmail(), user.getEmail());
        assertEquals(model.getPassword(), user.getPassword());
        assertEquals(model.getBirthday(), user.getBirthday());
        assertEquals(model.getUuid(), user.getUuid());
        assertEquals(model.getStatus(), user.getStatus());
        assertEquals(model.getInsertedAt(), user.getInsertedAt());
        assertEquals(model.getUpdatedAt(), user.getUpdatedAt());
        assertNotNull(model.getTodos());
        assertEquals(3, model.getTodos().size());
        assertNotNull(model.getPreferences());
    }

    @DisplayName("`ApplicationUser.toString()` must return a string in the valid pattern")
    @ParameterizedTest(name = "When the name is \"{0}\", email is \"{1}\" and status is \"{2}\" then the expected result is \"{3}\"")
    @MethodSource("userToStringPatternExpectedResults")
    void toStringMethodMustReturnAStringInTheValidPattern(String name, String email, Status status, String expected) {
        var user = new ApplicationUser();
        user.setName(name);
        user.setEmail(email);
        user.setStatus(status);

        assertEquals(expected, user.toString());
    }
    @DisplayName("`ApplicationUser.getTodos()` should return a `new ArrayList<>()` if the `todos` property is null")
    @Test
    void getTodosMethodShouldReturnANewArrayListIfTheTodosPropertyIsNull() {
        var user = new ApplicationUser();
        user.setTodos(null);

        assertEquals(user.getTodos(), new ArrayList<>());
    }

    @DisplayName("`ApplicationUser.toFlatModel()` should set the correct values into the model")
    @Test
    void toFlatModelMethodShouldSetTheCorrectValuesIntoTheModel() {
        var user = new ApplicationUser();
        var now = LocalDateTime.now();

        user.setName("Gusthawo");
        user.setEmail("test@test.com");
        user.setPassword("12345");
        user.setBirthday(LocalDate.of(2002, 10, 17));
        user.setUuid(UUID.randomUUID().toString());
        user.setStatus(Status.ACTIVE);
        user.setInsertedAt(now);
        user.setUpdatedAt(now);

        var model = user.toFlatModel();

        assertNotNull(model);
        assertEquals(model.getName(), user.getName());
        assertEquals(model.getEmail(), user.getEmail());
        assertEquals(model.getPassword(), user.getPassword());
        assertEquals(model.getBirthday(), user.getBirthday());
        assertEquals(model.getUuid(), user.getUuid());
        assertEquals(model.getStatus(), user.getStatus());
        assertEquals(model.getInsertedAt(), user.getInsertedAt());
        assertEquals(model.getUpdatedAt(), user.getUpdatedAt());
    }


    public static Stream<Object[]> userStatusIsBlockedExpectedResults() {
        return Stream.of(
            new Object[] { Status.ACTIVE, false },
            new Object[] { Status.INACTIVE, false },
            new Object[] { Status.BLOCKED, true }
        );
    }

    public static Stream<Object[]> userToStringPatternExpectedResults() {
        return Stream.of(
                new Object[] { "Gusthawo", "test@test.com", Status.ACTIVE,  "[Ativo] Gusthawo (test@test.com)" },
                new Object[] { "Fulano", "fulano@test.com", Status.INACTIVE,  "[Inativo] Fulano (fulano@test.com)" },
                new Object[] { "Ciclano", "ciclano@test.com", Status.BLOCKED,  "[Bloqueado] Ciclano (ciclano@test.com)" },
                new Object[] { "Beltrano", "beltrano@ciclano.com", Status.INACTIVE,  "[Inativo] Beltrano (beltrano@ciclano.com)" }
        );
    }

}