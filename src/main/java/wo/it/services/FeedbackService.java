package wo.it.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.interfaces.CRUDService;
import wo.it.database.entities.Feedback;
import wo.it.models.Classification;
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
    @Transactional
    public void update(Feedback entity) {
        repository.merge(entity);
    }

    @Override
    public void delete(String uuid) throws EntityNotFoundException {
        var feedback = repository.findByUuid(uuid);
        if (feedback == null) {
            throw new EntityNotFoundException("Evento '" + uuid + "' não encontrado, registro não pode ser excluído!");
        }
        repository.delete(feedback);
    }

    public List<Classification> calculate(String userUuid) {
        return repository.calculate(userUuid);
    }
}
