package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
    Iterable<Transaction> findByUser(User user);
}
