package wo.it.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import wo.it.database.models.ApplicationUser;

@ApplicationScoped
public class ApplicationUserRepository implements PanacheRepository<ApplicationUser> {

    public ApplicationUser findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
