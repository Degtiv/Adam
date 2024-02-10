package space.deg.adam.telegram;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class BotProperties {
  @Value("${bot.name}")
  String name;
  @Value("${bot.token}")
  String token;
}
