package wo.it.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.database.entities.ApplicationUser;
import wo.it.core.exceptions.EmptyParameterException;
import wo.it.core.exceptions.PersistException;
import wo.it.repositories.ApplicationUserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class ApplicationUserServiceTest {

    @Inject ApplicationUserService service;
    @InjectMock ApplicationUserRepository repository;


    @DisplayName("`AuthService.findByEmail()` must throw an exception if the `email` is blank")
    @ParameterizedTest(name = "When `credential.email` is \"{0}\" then the exception must be thrown")
    @NullSource
    @ValueSource(strings = {"", " "})
    void findByEmailMethodMustThrowAnExceptionIfTheEmailIsBlank(String email) {
        var exception = assertThrows(EmptyParameterException.class, () -> {
            service.findByEmail(email);
        });

        assertEquals("Por favor, informe em email para consultar um usuário", exception.getMessage());

    }

    @DisplayName("`ApplicationUserService.create()` must throw an exception when password is blank")
    @Test
    void createMethodMustThrowAnPersistExceptionWhenPasswordIsBlank() {
        var user = new ApplicationUser();
        user.setName("Gusthawo");
        user.setPassword(null);

        PersistException exception = assertThrows(PersistException.class, () -> {
            service.create(user);
        });

        assertEquals("A senha recebida para encriptação não pode ser vazia!", exception.getMessage());

        doNothing().when(repository).persist(user);
        verify(repository, times(0)).persist(user);
    }

    @DisplayName("`ApplicationUserService.findByUuid()` should return null if the `uuid` is blank")
    @ParameterizedTest(name = "When `uuid` is \"{0}\" then the user must be null")
    @NullSource
    @ValueSource(strings = {"", " "})
    void findByUuidMethodShouldReturnNullIfTheUuidIsBlank(String uuid) throws EmptyParameterException {
        when(repository.findByUuid(uuid)).thenReturn(null);
        var user = service.findByUuid(uuid);
        assertNull(user);
    }

}