package wo.it.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import wo.it.core.filter.TaskFilter;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Task;
import io.quarkus.panache.common.Sort;
import org.apache.commons.lang3.StringUtils;;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TaskRepository implements Repository<Task> {

    public Task findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }

    public List<Task> find(TaskFilter filter) {
        Map<String, Object> params = new HashMap<>();
        List<String> where = new ArrayList<>();
        var query = new StringBuilder();

        if (StringUtils.isNotBlank(filter.getUser())) {
            where.add("user.uuid = :user");
            params.put("user", filter.getUser());
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
