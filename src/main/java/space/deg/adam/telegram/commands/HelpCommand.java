package space.deg.adam.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.utils.BotConsts;

@Component
public class HelpCommand implements Command {
  @Override
  public String getText() {
    return "/help";
  }

  @Override
  public String getCommandCaption() {
    return "Get help";
  }

  @Override
  public SendMessage apply(Update update) {
    Message message = update.getMessage();
    long chatId = message.getChatId();

    return help(chatId);

  }

  @Override
  public SendMessage applyCallback(Update update) {
    Message callbackMessage = update.getCallbackQuery().getMessage();
    Long chatId = callbackMessage.getChatId();

    return help(chatId);
  }

  public SendMessage help(long chatId) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(BotConsts.HELP_INFO);

    return message;
  }
}
