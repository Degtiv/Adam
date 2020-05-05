package space.deg.adam.domain.user.events;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_events_first_enter")
@Data
public class FirstEnterUserEvent extends UserEvent {
    private static final String TITLE = "First Enter";

    public FirstEnterUserEvent() {
        super();
        title = TITLE;
        isActive = true;
        start = LocalDateTime.now().withYear(-10);
        end = LocalDateTime.now().withYear(10);
    }

    public static String getEventTitle() {
        return TITLE;
    }

    @Override
    public void act() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
