package space.deg.adam.telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import space.deg.adam.telegram.commands.Command;
import space.deg.adam.telegram.commands.Commands;
import space.deg.adam.telegram.commands.buttons.Buttons;
import space.deg.adam.telegram.utils.BotConsts;

@Component
public class CommandsHandler {
  @Autowired
  private Commands commands;

  @Autowired
  private Buttons buttons;

  public SendMessage handleCommand(Update update) {
    Message message = update.getMessage();
    long chatId = message.getChatId();
    String commandText = message.getText().split(" ")[0];
    Command commandHandler = findCommandByText(commandText);

    if (commandHandler != null) {
      SendMessage handled = commandHandler.apply(update);
//      handled.setReplyMarkup(buttons.inlineMarkup());
      return handled;
    }

    return new SendMessage(String.valueOf(chatId), BotConsts.UNKNOWN_COMMAND);
  }

  public SendMessage handleCallback(Update update) {
    Message callbackMessage = update.getCallbackQuery().getMessage();
    String chatId = callbackMessage.getChatId().toString();
    String receivedMessage = update.getCallbackQuery().getData();

    Command commandHandler = findCommandByText(receivedMessage);

    if (commandHandler != null) {
      SendMessage handled = commandHandler.applyCallback(update);
//      handled.setReplyMarkup(buttons.inlineMarkup());
      return handled;
    }

    return new SendMessage(chatId, BotConsts.UNKNOWN_COMMAND);
  }

  public Command findCommandByText(String text) {
    return commands.getCommands().stream().filter(x -> x.getText().equalsIgnoreCase(text)).findFirst().orElse(null);
  }
}
