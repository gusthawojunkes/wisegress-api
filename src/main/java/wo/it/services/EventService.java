package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import wo.it.core.exceptions.EmptyParameterException;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.filter.EventFilter;
import wo.it.core.interfaces.CRUDService;
import wo.it.database.entities.Event;
import wo.it.repositories.EventRepository;

import java.util.List;

@ApplicationScoped
public class EventService implements CRUDService<Event>  {

    @Inject EventRepository repository;

    @Transactional
    @Override
    public void create(Event entity) throws PersistException {
        repository.persist(entity);
    }

    @Transactional
    @Override
    public void update(Event entity) {
        repository.merge(entity);
    }

    @Transactional
    @Override
    public void delete(String uuid) throws EntityNotFoundException, EmptyParameterException {
        var event = this.findByUuid(uuid);
        if (event == null) {
            throw new EntityNotFoundException("Evento '" + uuid + "' não encontrado, registro não pode ser excluído!");
        }
        repository.delete(event);
    }

    public Event findByUuid(String uuid) throws EmptyParameterException {
        return repository.findByUuid(uuid);
    }

    public List<Event> find(EventFilter filter) {
        return repository.find(filter);
    }
}
