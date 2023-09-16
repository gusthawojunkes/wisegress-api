package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.*;
import wo.it.database.entities.ApplicationUser;
import wo.it.models.authentication.Formulary;
import wo.it.models.authentication.Password;
import wo.it.repositories.ApplicationUserRepository;

import java.util.List;

@ApplicationScoped
public class ApplicationUserService implements CRUDService<ApplicationUser> {

    @Inject ApplicationUserRepository applicationUserRepository;

    public ApplicationUser findByEmail(String email) throws EmptyParameterException {
        if (StringUtils.isBlank(email)) throw new EmptyParameterException("Por favor, informe em email para consultar um usuário");
        return applicationUserRepository.findByEmail(email);
    }

    public ApplicationUser register(Formulary formulary) throws EmptyParameterException, UserAlreadyFoundException, InvalidFormularyException, PersistException {
        if (formulary == null || StringUtils.isBlank(formulary.getEmail())) {
            throw new EmptyParameterException("Por favor, informe um email para cadastrar o usuário!");
        }

        checkIfUserAlreadyExists(formulary.getEmail());
        checkIfTheFormularyHasCritics(formulary);

        var user = formulary.toNewUser();
        this.create(user);

        return user;
    }

    private static void checkIfTheFormularyHasCritics(Formulary formulary) throws InvalidFormularyException {
        formulary.validate();
        if (formulary.hasCritics()) {
            throw new InvalidFormularyException("Não foi possível realizar o cadastro, revise os dados!", formulary.getCritics());
        }
    }

    private void checkIfUserAlreadyExists(String email) throws EmptyParameterException, UserAlreadyFoundException {
        ApplicationUser user = this.findByEmail(email);
        if (user != null) {
            throw new UserAlreadyFoundException(String.format("Já existe um usuário cadastrado com o email \"%s\"", email));
        }
    }

    @Override
    @Transactional
    public void create(ApplicationUser entity) throws PersistException {
        try {
            String encryptedPassword = Password.encrypt(entity.getPassword());
            entity.setPassword(encryptedPassword);
        } catch (EncryptException e) {
            throw new PersistException(e.getMessage());
        }
        applicationUserRepository.persist(entity);
    }

    @Override
    public List<ApplicationUser> read() {
        throw new NotImplementedException();
    }

    @Override
    public void update(ApplicationUser entity) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(String uuid) {
        throw new NotImplementedException();
    }
}
