package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.filter.TodoFilter;
import wo.it.core.interfaces.CRUDService;
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
    @Transactional
    public void update(Todo entity) {
        repository.merge(entity);
    }

    @Override
    @Transactional
    public void delete(String uuid) throws EntityNotFoundException {
        var todo = repository.findByUuid(uuid);
        if (todo == null) {
            throw new EntityNotFoundException("Todo '" + uuid + "' não encontrado, registro não pode ser excluído!");
        }
        repository.delete(todo);
    }

    public List<Todo> find(TodoFilter filter) {
        return repository.find(filter);
    }

}
