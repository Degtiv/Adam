package space.deg.adam.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.user.User;

public interface OperationRepository extends CrudRepository<Operation, String> {
    Iterable<Operation> findByUser(User user, Sort sort);
    Iterable<Operation> findByUser(User user);
}
