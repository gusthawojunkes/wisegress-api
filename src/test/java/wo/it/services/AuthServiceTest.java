package wo.it.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.database.models.ApplicationUser;
import wo.it.exceptions.EmptyParameterException;
import wo.it.exceptions.InvalidFormularyException;
import wo.it.exceptions.UserAlreadyFoundException;
import wo.it.models.Status;
import wo.it.models.authentication.Credential;
import wo.it.models.authentication.Formulary;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
class AuthServiceTest {

    @Inject AuthService service;
    @InjectMock ApplicationUserService applicationUserService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("`AuthService.authenticate()` must throw an exeception if the `credential.email` parameter is blank")
    @ParameterizedTest(name = "When `credential.email` is \"{0}\" then the exception must be thrown")
    @NullSource
    @ValueSource(strings = {"", " "})
    void authenticateMethodMustThrowAnErrorIfTheEmailParameterIsNull(String email) throws EmptyParameterException {
        Credential credential = new Credential(email, "1234");

        when(applicationUserService.findByEmail(credential.email())).thenThrow(new EmptyParameterException("Por favor, informe em email para consultar um usuário"));
        var response = service.authenticate(credential);

        var exception = assertThrows(EmptyParameterException.class, () -> {
            applicationUserService.findByEmail(credential.email());
        });
        assertTrue(response.hasErrors());
        assertTrue(response.hasCritics());
        assertFalse(response.isSuccess());
        assertEquals("Por favor, informe em email para consultar um usuário", response.getMessage());
        assertEquals("Por favor, informe em email para consultar um usuário", exception.getMessage());
    }

    @DisplayName("`AuthService.authenticate()` must return an invalid response if the user is not found")
    @Test
    void authenticateMethodMustReturnAnErrorIfTheUserIsNotFound() throws EmptyParameterException {
        Credential credential = new Credential("test@test.com", "1234");

        when(applicationUserService.findByEmail("test@test.com")).thenReturn(null);
        var response = service.authenticate(credential);

        assertTrue(response.hasErrors());
        assertTrue(response.hasCritics());
        assertFalse(response.isSuccess());
        assertEquals("Usuário não encontrado no sistema!", response.getMessage());
    }

    @DisplayName("`AuthService.authenticate()` must return an invalid response if the user is blocked")
    @Test
    void authenticateMethodMustReturnAnErrorIfTheUserIsBlocked() throws EmptyParameterException {
        Credential credential = new Credential("test@test.com", "1234");

        var blockedUser = new ApplicationUser();
        blockedUser.setStatus(Status.BLOCKED);

        when(applicationUserService.findByEmail("test@test.com")).thenReturn(blockedUser);
        var response = service.authenticate(credential);

        assertTrue(response.hasErrors());
        assertTrue(response.hasCritics());
        assertFalse(response.isSuccess());
        assertEquals("Usuário bloqueado pelo sistema, entre em contato com o suporte!", response.getMessage());
    }

    @DisplayName("`AuthService.authenticate()` must return a valid response")
    @Test
    void authenticateMethodMustReturnAValidResponse() throws EmptyParameterException {
        Credential credential = new Credential("test@test.com", "1234");

        var user = new ApplicationUser();
        user.setEmail(credential.email());
        user.setPassword(credential.password());
        user.setStatus(Status.ACTIVE);

        when(applicationUserService.findByEmail("test@test.com")).thenReturn(user);
        var response = service.authenticate(credential);

        assertTrue(response.isSuccess());
        assertFalse(response.hasErrors());
        assertFalse(response.hasCritics());
    }

    @DisplayName("`AuthService.register()` must throw an exception if the email is blank")
    @ParameterizedTest(name = "When `formulary.email` is \"{0}\" then the exception must be thrown")
    @NullSource
    @ValueSource(strings = {"", " "})
    void registerMethodMustThrowAnExceptionIfTheEmailParameterIsNull(String email) throws EmptyParameterException, UserAlreadyFoundException, InvalidFormularyException {
        Formulary formulary = new Formulary(email, "1234");

        when(applicationUserService.register(formulary)).thenThrow(new EmptyParameterException("Por favor, informe um email para cadastrar o usuário!"));

        var response = service.register(formulary);
        var exception = assertThrows(EmptyParameterException.class, () -> {
            applicationUserService.register(formulary);
        });

        assertTrue(response.isError());
        assertTrue(response.hasCritics());
        assertFalse(response.isSuccess());
        assertEquals("Por favor, informe um email para cadastrar o usuário!", response.getMessage());
        assertEquals("Por favor, informe um email para cadastrar o usuário!", exception.getMessage());
    }

    @DisplayName("`AuthService.register()` must throw an exception if the user already exists")
    @Test
    void registerMethodMustThrowAnExceptionIfTheUserAlreadyExists() throws EmptyParameterException, UserAlreadyFoundException, InvalidFormularyException {
        Formulary formulary = new Formulary("already@exists.com", "1234");

        var message = String.format("Já existe um usuário cadastrado com o email \"%s\"", formulary.getEmail());

        var user = new ApplicationUser();
        user.setEmail(formulary.getEmail());
        user.setPassword(formulary.getPassword());
        user.setStatus(Status.ACTIVE);

        when(applicationUserService.register(formulary)).thenThrow(new UserAlreadyFoundException(message));

        var response = service.register(formulary);
        assertTrue(response.isError());
        assertTrue(response.hasCritics());
        assertFalse(response.isSuccess());
        assertEquals(message, response.getMessage());
    }

}