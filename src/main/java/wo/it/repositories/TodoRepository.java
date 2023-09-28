package wo.it.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Todo;

@ApplicationScoped
public class TodoRepository implements Repository<Todo> {

    public Todo findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }

}
