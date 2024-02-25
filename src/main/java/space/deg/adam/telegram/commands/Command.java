package space.deg.adam.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
  String getText();
  String getCommandCaption();
  SendMessage apply(Update update);
  SendMessage applyCallback(Update update);
  default void changeChatState(String chatId) {}
}
