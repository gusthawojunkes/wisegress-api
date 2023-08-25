package wo.it.services;

import java.util.List;

public interface CRUDService<T> {

    void create(T entity);
    List<T> read();
    void update(T entity);
    void delete(String uuid);

}
