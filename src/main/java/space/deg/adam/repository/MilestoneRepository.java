package space.deg.adam.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.balance.Milestone;
import space.deg.adam.domain.user.User;

import java.time.LocalDateTime;

public interface MilestoneRepository extends CrudRepository<Milestone, String> {
    Iterable<Milestone> findByUser(User user);
    Iterable<Milestone> findByUserAndDateAfter(User user, LocalDateTime date);
    Iterable<Milestone> findByUserAndDateBefore(User user, LocalDateTime date, Sort sort);
    Iterable<Milestone> findByUserAndDate(User user, LocalDateTime date, Sort sort);

    Iterable<Milestone> findByUser(User user, Sort sort);
}
