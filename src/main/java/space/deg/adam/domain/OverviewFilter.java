package space.deg.adam.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import space.deg.adam.domain.user.User;

@Entity
@Table(name = "overview_filters")
@Data
public class OverviewFilter {
    @Id
    @Column(length = 100)
    protected String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    protected User user;

    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime fromDate;

    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime toDate;

    protected Boolean showDots = false;
    protected Boolean isActive = false;

    public OverviewFilter() {
        this.uuid = UUID.randomUUID().toString();
        clear();
    }

    public void setup(LocalDateTime fromDate, LocalDateTime toDate, Boolean showDots) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        isActive = true;
        this.showDots = showDots;
    }

    public void clear() {
        isActive = false;
    }

    public String getFromDateField() {
        return fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getToDateField() {
        return toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
