package space.deg.adam.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import space.deg.adam.telegram.commands.buttons.Buttons;
import space.deg.adam.telegram.handlers.CommandsHandler;
import space.deg.adam.telegram.handlers.DialogHandler;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
  public final BotProperties botProperties;
  public final CommandsHandler commandsHandler;
  public final DialogHandler dialogHandler;
  public final Buttons buttons;

  TelegramBot(BotProperties botProperties,
              CommandsHandler commandsHandler,
              DialogHandler dialogHandler,
              Buttons buttons) {
    super(botProperties.token);
    this.botProperties = botProperties;
    this.commandsHandler = commandsHandler;
    this.dialogHandler = dialogHandler;
    this.buttons = buttons;

    try {
      this.execute(new SetMyCommands(buttons.getCommandsList(), new BotCommandScopeDefault(), null));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    Message message = update.getMessage();
    if (message != null && message.hasText()) {
      if (message.getText().startsWith("/")) {
        sendMessage(commandsHandler.handleCommand(update));
      } else {
        sendMessage(dialogHandler.handleDialog(update));
      }

      return;
    }

    if (update.hasCallbackQuery()) {
      sendMessage(commandsHandler.handleCallback(update));
    }
  }

  @Override
  public String getBotUsername() {
    return botProperties.getName();
  }

  private void sendMessage(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(e.getMessage());
    }
  }
}
