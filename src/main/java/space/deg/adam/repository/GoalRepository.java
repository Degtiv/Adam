package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.user.User;

public interface GoalRepository extends CrudRepository<Goal, String> {
    Iterable<Goal> findByUser(User user);
}
