package wo.it.core.interfaces;

import wo.it.core.exceptions.EmptyParameterException;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;

public interface CRUDService<T> {

    void create(T entity) throws PersistException;
    void update(T entity);
    void delete(String uuid) throws EntityNotFoundException, EmptyParameterException;

}
