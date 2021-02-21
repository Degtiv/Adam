package space.deg.adam.domain.token;

import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.user.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Data
public class Token {
    private static final Long STANDARD_DURATION_45_MINUTES = 45 * 60L;

    @Id
    @Column(length = 100)
    protected String uuid;

    protected String username;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    @NonNull
    protected LocalDateTime startDate;

    //in seconds
    protected Long duration;
    protected Role role;

    public Token() {
        this.uuid = UUID.randomUUID().toString();
        duration = STANDARD_DURATION_45_MINUTES;
    }

    public boolean isActive() {
        return LocalDateTime.now().isBefore(getExpirationDateTime());
    }

    public LocalDateTime getExpirationDateTime() {
        return startDate.plusSeconds(duration);
    }

    public String toString() {
        return "Token{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", startTime=" + startDate.format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                ", startDate=" + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
                ", isActive=" + isActive() + "}";
    }
}
