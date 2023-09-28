package wo.it.core.interfaces;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface Repository<Entity> extends PanacheRepository<Entity> {

    default void merge(Entity entity) {
        this.getEntityManager().merge(entity);
    }
}
