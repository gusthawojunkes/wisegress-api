package wo.it.services;

import wo.it.exceptions.PersistException;

import java.util.List;

public interface CRUDService<T> {

    void create(T entity) throws PersistException;
    List<T> read();
    void update(T entity);
    void delete(String uuid);

}
