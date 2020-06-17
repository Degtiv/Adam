package space.deg.adam.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.transaction.goals.Goal;
import space.deg.adam.domain.user.User;

import java.time.LocalDateTime;

public interface GoalRepository extends CrudRepository<Goal, String> {
    Iterable<Goal> findByUser(User user);
    Iterable<Goal> findByUser(User user, Sort sort);
    Iterable<Goal> findByUserAndDateAfterAndDateBefore(User user, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
}
