package wo.it.core.interfaces;

import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;

import java.util.List;

public interface CRUDService<T> {

    void create(T entity) throws PersistException;
    List<T> read();
    void update(T entity);
    void delete(String uuid) throws EntityNotFoundException;

}
