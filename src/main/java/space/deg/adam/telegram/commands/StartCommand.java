package space.deg.adam.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.utils.BotUtils;

@Component
public class StartCommand implements Command {

  @Override
  public String getText() {
    return "/start";
  }

  @Override
  public String getCommandCaption() {
    return "Start bot";
  }

  @Override
  public SendMessage apply(Update update) {
    Message message = update.getMessage();
    long chatId = message.getChatId();
    String memberName = message.getFrom().getFirstName();

    return startBot(chatId, memberName);

  }

  @Override
  public SendMessage applyCallback(Update update) {
    Message callbackMessage = update.getCallbackQuery().getMessage();
    Long chatId = callbackMessage.getChatId();
    String username = callbackMessage.getFrom().getFirstName();

    return startBot(chatId, username);
  }

  public SendMessage startBot(long chatId, String userName) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(BotUtils.greetings(userName));

    return message;
  }
}
