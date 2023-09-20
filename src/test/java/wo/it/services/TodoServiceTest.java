package wo.it.services;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.core.exceptions.EmptyParameterException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TodoServiceTest {

    @Inject TodoService service;

    @DisplayName("`TodoService.findByUuid()` should return null if the `uuid` is blank")
    @ParameterizedTest(name = "When `uuid` is \"{0}\" then the returned value is null")
    @NullSource
    @ValueSource(strings = {"", " "})
    void findByUuidMethodMustNullIfTheUuidIsBlank(String uuid) {
        var todo = service.findByUuid(uuid);
        assertNull(todo);
    }

}