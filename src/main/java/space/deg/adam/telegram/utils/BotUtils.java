package space.deg.adam.telegram.utils;

public class BotUtils {
  public static String greetings(String username) {
    return "Добрый день, " + username + "! Я телеграм-бот, который помогает планировать финансы";
  }

  public static String greetingsIntegrated(String siteUser, String tgUser) {
    return "Добрый день, " + siteUser + "! Теперь я знаю, что в телеграме вы - " + tgUser + ".";
  }
}
