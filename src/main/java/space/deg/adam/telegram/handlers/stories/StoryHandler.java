package space.deg.adam.telegram.handlers.stories;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StoryHandler {
  SendMessage handle(Update update);
  boolean checkCondition(Update update);
}
