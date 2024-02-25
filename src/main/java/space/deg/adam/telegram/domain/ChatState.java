package space.deg.adam.telegram.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "chat_state")
@Data
@NoArgsConstructor
public class ChatState {
  @Id
  @Column(length = 40)
  private String chatId;

  @Column(length = 1000)
  private String lastCommandState;

  public ChatState(String chatId) {
    this.chatId = chatId;
  }
}
