package space.deg.adam.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.user.User;

public interface RuleRepository extends CrudRepository<Rule, String> {
    Iterable<Rule> findByUser(User user, Sort sort);
    Iterable<Rule> findByUser(User user);
}
