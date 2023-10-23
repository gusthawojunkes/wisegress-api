package wo.it.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import wo.it.core.interfaces.Repository;
import wo.it.database.entities.Feedback;

@ApplicationScoped
public class FeedbackRepository implements Repository<Feedback> {
}
