package wo.it.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.EmptyParameterException;
import wo.it.database.entities.ApplicationUser;

@ApplicationScoped
public class ApplicationUserRepository implements PanacheRepository<ApplicationUser> {

    public ApplicationUser findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public ApplicationUser findByUuid(String uuid) throws EmptyParameterException {
        if (StringUtils.isBlank(uuid)) throw new EmptyParameterException("É necessário um uuid para consultar o usuário!");
        return find("uuid", uuid).firstResult();
    }
}
