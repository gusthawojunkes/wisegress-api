package wo.it.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import wo.it.database.models.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
