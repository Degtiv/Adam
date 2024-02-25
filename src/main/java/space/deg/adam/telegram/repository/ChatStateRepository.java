package space.deg.adam.telegram.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.telegram.domain.ChatState;

public interface ChatStateRepository extends CrudRepository<ChatState, String> {
  ChatState findByChatId(String chatId);

}
