package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
