package space.deg.adam.telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.telegram.handlers.stories.StoryHandler;
import space.deg.adam.telegram.services.ChatStateService;

@Component
public class DialogHandler {

  @Autowired
  ChatStateService chatStateService;

  @Autowired
  Stories stories;

  public SendMessage handleDialog(Update update) {
    Message message = update.getMessage();
    long chatId = message.getChatId();

    String response = baseResponse(message.getText());

    for (StoryHandler storyHandler : stories.getStories()) {
      if (storyHandler.checkCondition(update))
        return storyHandler.handle(update);
    }

    return new SendMessage(String.valueOf(chatId), response);
  }

  public String baseResponse(String question) {
    int maxIndex = Math.min(question.length(), 10);
    return "Ты сказал: " + question.subSequence(0, maxIndex) + "...";
  }
}
