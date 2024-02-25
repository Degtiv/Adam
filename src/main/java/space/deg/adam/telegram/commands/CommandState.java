package space.deg.adam.telegram.commands;

public enum CommandState {
  NO_STATE,
  AUTH_KEY_WAITING;

  public static CommandState byName(String name) {
    for (CommandState category : values()) {
      if (category.name().equals(name)) {
        return category;
      }
    }
    throw new IllegalArgumentException();
  }

}
