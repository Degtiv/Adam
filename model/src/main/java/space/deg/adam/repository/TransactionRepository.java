package space.deg.adam.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;

import java.time.LocalDateTime;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
    Iterable<Transaction> findByUser(User user, Sort sort);
    Iterable<Transaction> findByUserAndDateAfterAndDateBefore(User user, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
    Iterable<Transaction> findByUserAndDate(User user, LocalDateTime date, Sort sort);
    Iterable<Transaction> findByUser(User user);
}
