package wo.it.repositories;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.commons.collections4.CollectionUtils;
import wo.it.core.enums.Feature;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Feedback;
import wo.it.models.Classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class FeedbackRepository implements Repository<Feedback> {

    @Inject EntityManager entityManager;

    public Feedback findByUuid(String uuid) {
        return find("uuid", uuid).firstResult();
    }

    public List<Classification> calculate(String userUuid) {
        List<Classification> classifications = new ArrayList<>();
        try {
            StringBuilder select = new StringBuilder();
            select.append("SELECT feedback.feature, SUM(feedback.rating) ");
            select.append("FROM Feedback feedback ");
            select.append("WHERE feedback.user_uuid = :user ");
            select.append("GROUP BY feedback.feature ");
            select.append("ORDER BY SUM(feedback.rating) DESC ");

            Query query  = entityManager.createNativeQuery(select.toString());
            query.setParameter("user", userUuid);
            List<Object[]> result = query.getResultList();
            if (CollectionUtils.isEmpty(result)) return new ArrayList<>();

            for (Object[] row : result) {
                Short digit = (Short) row[0];
                Long points = (Long) row[1];

                Feature feature = Feature.getByDigit(digit);
                Classification classification = new Classification(feature, points);
                classifications.add(classification);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return classifications;
    }

}
