package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.interfaces.CRUDService;
import wo.it.database.entities.Feedback;
import wo.it.repositories.FeedbackRepository;

import java.util.List;

@ApplicationScoped
public class FeedbackService implements CRUDService<Feedback> {

    @Inject FeedbackRepository repository;

    @Override
    @Transactional
    public void create(Feedback entity) throws PersistException {
        repository.persist(entity);
    }

    @Override
    public List<Feedback> read() {
        return null;
    }

    @Override
    public void update(Feedback entity) {

    }

    @Override
    public void delete(String uuid) throws EntityNotFoundException {

    }
}
