package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import wo.it.database.entities.ApplicationUser;
import wo.it.exceptions.EmptyParameterException;
import wo.it.exceptions.InvalidFormularyException;
import wo.it.exceptions.PersistException;
import wo.it.exceptions.UserAlreadyFoundException;
import wo.it.models.ApplicationUserModel;
import wo.it.models.CommonValidationResponse;
import wo.it.models.authentication.Credential;
import wo.it.models.authentication.Formulary;
import wo.it.models.authentication.RegistrationResponse;

@ApplicationScoped
public class AuthService {

    @Inject ApplicationUserService applicationUserService;

    public CommonValidationResponse authenticate(Credential credentials) {
        var response = CommonValidationResponse.initWithSuccess();

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
