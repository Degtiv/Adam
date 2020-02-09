package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.balance.Balance;
import space.deg.adam.domain.user.User;

public interface BalanceRepository extends CrudRepository<Balance, String> {
    Iterable<Balance> findByUser(User user);
}
