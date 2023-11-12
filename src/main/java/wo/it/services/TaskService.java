package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.filter.TaskFilter;
import wo.it.core.interfaces.CRUDService;
import wo.it.database.entities.Task;
import wo.it.repositories.TaskRepository;

import java.util.List;

@ApplicationScoped
public class TaskService implements CRUDService<Task> {

    @Inject TaskRepository repository;

    @Override
    @Transactional
    public void create(Task entity) throws PersistException {
        repository.persist(entity);
    }

    @Override
    @Transactional
    public void update(Task entity) {
        repository.merge(entity);
    }

    @Override
    @Transactional
    public void delete(String uuid) throws EntityNotFoundException {
        var task = repository.findByUuid(uuid);
        if (task == null) {
            throw new EntityNotFoundException("Task '" + uuid + "' não encontrado, registro não pode ser excluído!");
        }
        repository.delete(task);
    }

    public Task findByUuid(String uuid) {
        if (StringUtils.isBlank(uuid)) return null;
        return repository.findByUuid(uuid);
    }

    public List<Task> find(TaskFilter filter) {
        return repository.find(filter);
    }
}
