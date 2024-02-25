package space.deg.adam.telegram.commands;

import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class Commands {
  @Getter
  private final List<Command> commands;

  public Commands(StartCommand startCommand, HelpCommand helpCommand, AuthCommand authCommand) {
    this.commands = List.of(
        startCommand,
        helpCommand,
        authCommand);
  }
}
