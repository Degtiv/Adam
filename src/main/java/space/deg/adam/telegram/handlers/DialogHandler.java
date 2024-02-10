package space.deg.adam.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DialogHandler {
  public SendMessage handleDialog(Update update) {
    Message message = update.getMessage();
    long chatId = message.getChatId();

    return new SendMessage(String.valueOf(chatId), response(message.getText()));
  }

  public String response(String question) {
    int maxIndex = Math.min(question.length(), 10);
    return "Ты сказал: " + question.subSequence(0, maxIndex) + "...";
  }
}
