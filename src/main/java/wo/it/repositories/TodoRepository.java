package wo.it.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import wo.it.database.entities.Todo;

@ApplicationScoped
public class TodoRepository implements PanacheRepository<Todo> {

    public Todo findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }

}
