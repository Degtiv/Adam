package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.account.Account;

public interface AccountRepository extends CrudRepository<Account, String> {
    Iterable<Account> findByUser_Credentials_Username(String username);
    Account findByTitle(String title);
}
