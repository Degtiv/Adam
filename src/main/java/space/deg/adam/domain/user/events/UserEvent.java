package space.deg.adam.domain.user.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class UserEvent {
    @Id
    @Column(length = 100)
    protected String uuid;
    protected String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    protected User user;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    protected LocalDateTime start;
    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    protected LocalDateTime end;
    public boolean isActive;

    public UserEvent() {
        this.uuid = UUID.randomUUID().toString();
    }

    protected void act() {
        validateActiveDate();
    }

    private void validateActiveDate() {
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(start) && now.isBefore(end)) {
            isActive = false;
        }
    }

    @Override
    public String toString() {
        return title;
    }
}
