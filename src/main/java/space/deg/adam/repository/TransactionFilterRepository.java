package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.transaction.filter.TransactionFilter;
import space.deg.adam.domain.transaction.filter.TransactionFilterType;
import space.deg.adam.domain.user.User;

public interface TransactionFilterRepository extends CrudRepository<TransactionFilter, String> {
    TransactionFilter findByUserAndType(User user, TransactionFilterType type);
}
