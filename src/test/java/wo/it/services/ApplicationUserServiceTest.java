package wo.it.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import wo.it.exceptions.EmptyParameterException;
import wo.it.repositories.ApplicationUserRepository;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ApplicationUserServiceTest {

    @Inject ApplicationUserService service;
    @InjectMock ApplicationUserRepository applicationUserRepository;


    @DisplayName("`AuthService.authenticate()` must fill the response with an error if the `credential.email` parameter is blank")
    @ParameterizedTest(name = "When `credential.email` is \"{0}\" then the exception must be thrown")
    @NullSource
    @ValueSource(strings = {"", " "})
    void findByEmailMethodMustThrowAnExceptionIfTheEmailIsBlank(String email) {
        var exception = assertThrows(EmptyParameterException.class, () -> {
            service.findByEmail(email);
        });

        assertEquals("Por favor, informe em email para consultar um usuário", exception.getMessage());

    }

}