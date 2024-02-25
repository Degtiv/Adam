package space.deg.adam.telegram.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import space.deg.adam.domain.user.User;


@Entity
@Table(name = "user_chats")
@Data
public class UserChats {
  @Id
  @Column(length = 40)
  private String uuid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @ElementCollection(fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<String> chatIds = new HashSet<>();

  public void addChat(String chatId) {
    chatIds.add(chatId);
  }

  public UserChats() {
    this.uuid = UUID.randomUUID().toString();
  }
}
