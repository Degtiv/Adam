package space.deg.adam.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.user.User;

import java.time.LocalDateTime;

public interface GoalRepository extends CrudRepository<Goal, String> {
    Iterable<Goal> findByUser(User user);
    Iterable<Goal> findByUserAndDateAfterAndDateBefore(User user, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
}
