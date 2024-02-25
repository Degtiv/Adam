package space.deg.adam.telegram.handlers.stories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.telegram.commands.CommandState;
import space.deg.adam.telegram.services.ChatStateService;
import space.deg.adam.telegram.services.UserChatsService;
import space.deg.adam.telegram.utils.BotConsts;
import space.deg.adam.utils.encryption.EncryptionPrefixes;

@Component
public class WaitingApiHandler implements StoryHandler {

  @Autowired
  ChatStateService chatStateService;

  @Autowired
  UserChatsService userChatsService;

  @Override
  public boolean checkCondition(Update update) {
    String chatId = update.getMessage().getChatId().toString();
    CommandState commandState = chatStateService.getChatState(chatId);
    return commandState == CommandState.AUTH_KEY_WAITING &&
        (!update.getMessage().getText().contains(EncryptionPrefixes.TG_API_KEY_PREFIX) ||
            !update.getMessage().getText().contains(EncryptionPrefixes.TG_API_KEY_SUFFIX));
  }

  @Override
  public SendMessage handle(Update update) {
    String chatId = update.getMessage().getChatId().toString();
    return new SendMessage(chatId, BotConsts.AUTH_INFO);
  }
}
