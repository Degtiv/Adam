package space.deg.adam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.deg.adam.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
