package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.response.authentication.AuthValidationResponse;
import wo.it.core.response.authentication.RegistrationResponse;
import wo.it.database.entities.ApplicationUser;
import wo.it.core.exceptions.EmptyParameterException;
import wo.it.core.exceptions.InvalidFormularyException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.exceptions.UserAlreadyFoundException;
import wo.it.models.ApplicationUserModel;
import wo.it.models.authentication.*;

import java.util.HashSet;

@ApplicationScoped
public class AuthService {

    @Inject ApplicationUserService applicationUserService;

    public AuthValidationResponse authenticate(Credential credentials) {
        AuthValidationResponse response = AuthValidationResponse.init();

        ApplicationUser user;
        try {
            user = applicationUserService.findByEmail(credentials.email());
        } catch (EmptyParameterException exception) {
            response.makeInvalid();
            response.setMessage(exception.getMessage());
            return response;
        }

        if (user == null) {
            response.makeInvalid();
            response.setMessage("Usuário não encontrado no sistema!");
            return response;
        }

        if (user.isBlocked()) {
            response.makeInvalid();
            response.setMessage("Usuário bloqueado pelo sistema, entre em contato com o suporte!");
            return response;
        }

        if (!StringUtils.equals(user.getPassword(), credentials.getEncryptedPassword())) {
            response.makeInvalid();
            response.setMessage("Senha incorreta!");
            return response;
        }

        response.makeValid();
        response.setUser(user.toModel());
        try {
            var token = Token.generate(user.getName(), new HashSet<>(), 3600L, user.getEmail());
            response.setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public RegistrationResponse register(Formulary formulary) {
        var response = new RegistrationResponse();

        try {
            var user = applicationUserService.register(formulary);
            var model = ApplicationUserModel.loadFrom(user);
            response.setUser(model);
        } catch (EmptyParameterException | UserAlreadyFoundException | PersistException exception) {
            response.makeInvalid();
            response.setMessage(exception.getMessage());
        } catch (InvalidFormularyException invalidFormularyException) {
            response.makeInvalid();
            response.setMessage(invalidFormularyException.getMessage());
            response.setCritics(invalidFormularyException.getCritics());
        }

        return response;
    }

}
