package space.deg.adam.telegram.handlers.stories;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.domain.user.User;
import space.deg.adam.service.UserService;
import space.deg.adam.telegram.commands.CommandState;
import space.deg.adam.telegram.services.ChatStateService;
import space.deg.adam.telegram.services.UserChatsService;
import space.deg.adam.telegram.utils.BotConsts;
import space.deg.adam.telegram.utils.BotUtils;
import space.deg.adam.utils.encryption.EncryptionPrefixes;

@Component
public class AddApiHandler implements StoryHandler {

  @Autowired
  UserService userService;

  @Autowired
  ChatStateService chatStateService;

  @Autowired
  UserChatsService userChatsService;

  @Override
  public boolean checkCondition(Update update) {
    return update.getMessage().getText().contains(EncryptionPrefixes.TG_API_KEY_PREFIX)
        && update.getMessage().getText().contains(EncryptionPrefixes.TG_API_KEY_SUFFIX);
  }

  @Override
  public SendMessage handle(Update update) {
    String chatId = update.getMessage().getChatId().toString();
    chatStateService.changeChatState(chatId, CommandState.NO_STATE);

    String token = getTokenFromMessage(update.getMessage().getText());

    try {
      User user = userService.loadUserByAccessToken(token);
      userChatsService.addChat(user, chatId);

      return new SendMessage(chatId, BotUtils.greetingsIntegrated(user.getUsername(),
          update.getMessage().getFrom().getFirstName()));

    } catch (IllegalArgumentException ignored) {}

    return new SendMessage(chatId, BotConsts.API_KEY_NOT_EXISTS);
  }

  public String getTokenFromMessage(String message) {
    Pattern pattern = Pattern.compile("(0x1o7.*3n4)");
    return pattern.matcher(message)
        .results()
        .map(x -> x.group(1)).findFirst().get();
  }
}
