package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import wo.it.database.models.User;
import wo.it.models.authentication.AuthenticationResponse;
import wo.it.models.authentication.Credential;
import wo.it.repositories.UserRepository;

@ApplicationScoped
public class AuthService {

    @Inject UserRepository userRepository;

    public AuthenticationResponse authenticate(Credential credentials) {
        var response = new AuthenticationResponse();

        User user = userRepository.findByEmail(credentials.getSanitizedEmail());

        if (user.isBlocked()) {
            response.setMessage("Usuário bloqueado pelo sistema, entre em contato com o suporte!");
        }

        return response;
    }

}
