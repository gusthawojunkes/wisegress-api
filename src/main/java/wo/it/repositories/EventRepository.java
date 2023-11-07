package wo.it.repositories;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.filter.EventFilter;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EventRepository implements Repository<Event>  {

    public Event findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }

    public List<Event> find(EventFilter filter) {
        Map<String, Object> params = new HashMap<>();
        List<String> where = new ArrayList<>();
        var query = new StringBuilder();

        if (StringUtils.isNotBlank(filter.getUser())) {
            where.add("user.uuid = :user");
            params.put("user", filter.getUser());
        }

        if (filter.getDate() != null) {
            where.add("date = :date");
            params.put("date", filter.getDate());
        }

        if (filter.getStartDate() != null) {
            where.add("startDate = :startDate");
            params.put("startDate", filter.getStartDate());
        }

        if (filter.getEndDate() != null) {
            where.add("endDate = :endDate");
            params.put("endDate", filter.getEndDate());
        }

        if (StringUtils.isNotBlank(filter.getDescription())) {
            where.add("description = :description");
            params.put("description", filter.getUser());
        }

        String connector = "";
        for (String param : where) {
            query.append(connector).append(param);
            connector = " AND ";
        }

        return this.find(query.toString(), Sort.by("insertedAt").ascending(), params).list();
    }
}
