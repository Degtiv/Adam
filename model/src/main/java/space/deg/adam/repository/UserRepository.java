package space.deg.adam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.deg.adam.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByCredentials_Username(String username);
}
