package wo.it.repositories;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.filter.TodoFilter;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Todo;

import java.util.*;

@ApplicationScoped
public class TodoRepository implements Repository<Todo> {

    public Todo findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }

    public List<Todo> find(TodoFilter filter) {
        Map<String, Object> params = new HashMap<>();
        List<String> where = new ArrayList<>();
        var query = new StringBuilder();

        if (StringUtils.isNotBlank(filter.getUser())) {
            where.add("user.uuid = :user");
            params.put("user", filter.getUser());
        }

        if (filter.getDone() != null) {
            where.add("done = :done");
            params.put("done", filter.getDone());
        }

        if (filter.getCompletedAt() != null) {
            where.add("completedAt = :completedAt");
            params.put("completedAt", filter.getCompletedAt());
        }

        if (filter.getPriority() != null) {
            where.add("priority = :priority");
            params.put("priority", filter.getPriority());
        }

        if (CollectionUtils.isEmpty(where)) {
            return this.findAll().list();
        }

        String connector = "";
        for (String param : where) {
            query.append(connector).append(param);
            connector = " AND ";
        }

        return this.find(query.toString(), Sort.by("insertedAt").ascending(), params).list();
    }

}
