package space.deg.adam.telegram.commands.buttons;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import space.deg.adam.telegram.commands.Commands;

@Component
public class Buttons {
  @Autowired
  private Commands commands;

  public List<BotCommand> getCommandsList() {
    return commands.getCommands().stream()
        .map(x -> new BotCommand(x.getText(), x.getCommandCaption()))
        .collect(Collectors.toList());
  }

  public InlineKeyboardMarkup inlineMarkup() {
    List<InlineKeyboardButton> rowInline = commands.getCommands().stream().map(command -> {
      InlineKeyboardButton button = new InlineKeyboardButton(command.getCommandCaption());
      button.setCallbackData(command.getText());

      return button;
    }).collect(Collectors.toList());

    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    markup.setKeyboard(List.of(rowInline));

    return markup;
  }
}
