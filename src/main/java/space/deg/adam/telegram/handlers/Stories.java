package space.deg.adam.telegram.handlers;

import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;
import space.deg.adam.telegram.handlers.stories.AddApiHandler;
import space.deg.adam.telegram.handlers.stories.StoryHandler;
import space.deg.adam.telegram.handlers.stories.WaitingApiHandler;

@Component
public class Stories {
  @Getter
  private final List<StoryHandler> stories;

  public Stories(AddApiHandler addApiHandler, WaitingApiHandler waitingApiHandler) {
    this.stories = List.of(
        addApiHandler,
        waitingApiHandler);
  }
}
