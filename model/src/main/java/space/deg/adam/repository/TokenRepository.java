package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.token.Token;
import space.deg.adam.domain.user.Role;

public interface TokenRepository extends CrudRepository<Token, String> {
    Iterable<Token> findByUsername(String username);
    Token findByUsernameAndRole(String username, Role role);
}
