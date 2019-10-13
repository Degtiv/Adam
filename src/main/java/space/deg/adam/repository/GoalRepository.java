package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.Goal;

public interface GoalRepository extends CrudRepository<Goal, String> {
}
