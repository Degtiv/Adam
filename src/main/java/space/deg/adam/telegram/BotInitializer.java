package space.deg.adam.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class BotInitializer {
  @Autowired
  TelegramBot telegramBot;

  @EventListener({ContextStartedEvent.class})
  public void init() {
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(telegramBot);
    } catch (TelegramApiException e) {
      log.error(e.getMessage());
    }
  }
}
