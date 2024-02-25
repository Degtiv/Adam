package space.deg.adam.telegram.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.user.User;
import space.deg.adam.telegram.domain.UserChats;

public interface UserChatsRepository extends CrudRepository<UserChats, String> {
  UserChats findByUser(User User);

}
