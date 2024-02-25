package space.deg.adam.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.telegram.services.ChatStateService;
import space.deg.adam.telegram.utils.BotConsts;

@Component
public class AuthCommand implements Command {

  @Autowired
  ChatStateService chatStateService;

  @Override
  public String getText() {
    return "/auth";
  }

  @Override
  public String getCommandCaption() {
    return "Интеграция с сайтом";
  }

  @Override
  public SendMessage apply(Update update) {
    Message message = update.getMessage();
    long chatId = message.getChatId();
    changeChatState(String.valueOf(chatId));

    return auth(chatId);

  }

  @Override
  public SendMessage applyCallback(Update update) {
    Message callbackMessage = update.getCallbackQuery().getMessage();
    Long chatId = callbackMessage.getChatId();

    return auth(chatId);
  }

  public SendMessage auth(long chatId) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(BotConsts.AUTH_INFO);

    return message;
  }

  @Override
  public void changeChatState(String chatId) {
    chatStateService.changeChatState(chatId, CommandState.AUTH_KEY_WAITING);
  }
}
