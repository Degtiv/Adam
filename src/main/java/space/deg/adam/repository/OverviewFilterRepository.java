package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.OverviewFilter;
import space.deg.adam.domain.user.User;

public interface OverviewFilterRepository extends CrudRepository<OverviewFilter, String> {
    OverviewFilter findByUser(User user);
}
