package space.deg.adam.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.user.User;
import space.deg.adam.telegram.domain.UserChats;
import space.deg.adam.telegram.repository.UserChatsRepository;

@Service
public class UserChatsService {
  @Autowired
  private UserChatsRepository userChatsRepository;

  public void addChat(User user, String chatId) {
    UserChats userChats = userChatsRepository.findByUser(user);
    if (userChats == null) {
      userChats = new UserChats();
    }

    if (!userChats.getChatIds().contains(chatId)) {
      userChats.setUser(user);
      userChats.addChat(chatId);
      userChatsRepository.save(userChats);
    }
  }
}
