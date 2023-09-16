package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.EmptyParameterException;
import wo.it.core.exceptions.PersistException;
import wo.it.database.entities.Todo;
import wo.it.repositories.TodoRepository;

import java.util.List;

@ApplicationScoped
public class TodoService implements CRUDService<Todo> {

    @Inject TodoRepository repository;

    public Todo findByUuid(String uuid) {
        if (StringUtils.isBlank(uuid)) return null;
        return repository.findByUuid(uuid);
    }
    @Override
    @Transactional
    public void create(Todo entity) throws PersistException {
        repository.persist(entity);
    }

    @Override
    public List<Todo> read() {
        return null;
    }

    @Override
    public void update(Todo entity) {

    }

    @Override
    public void delete(String uuid) {

    }
}
