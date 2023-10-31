package wo.it.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Task;

@ApplicationScoped
public class TaskRepository implements Repository<Task> {

    public Task findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }


}
