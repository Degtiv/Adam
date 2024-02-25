package space.deg.adam.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.telegram.commands.CommandState;
import space.deg.adam.telegram.domain.ChatState;
import space.deg.adam.telegram.repository.ChatStateRepository;

@Service
public class ChatStateService {
  @Autowired
  private ChatStateRepository chatStateRepository;

  public void changeChatState(String chatId, CommandState commandState) {
    changeChatState(chatId, commandState.name());
  }

  public void changeChatState(String chatId, String newState) {
    space.deg.adam.telegram.domain.ChatState chatState = chatStateRepository.findByChatId(chatId);
    if (chatState == null) {
      chatState = new space.deg.adam.telegram.domain.ChatState(chatId);
    }
    chatState.setLastCommandState(newState);
    chatStateRepository.save(chatState);
  }

  public CommandState getChatState(String chatId) {
    ChatState chatState = chatStateRepository.findByChatId(chatId);
    if (chatState == null)
      return CommandState.NO_STATE;

    String state = chatState.getLastCommandState();
    try {
      return CommandState.byName(state);
    } catch (Exception ignored) {}

    return CommandState.NO_STATE;
  }
}
