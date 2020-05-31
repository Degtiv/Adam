package space.deg.adam.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "transaction_filters")
@Data
public class TransactionFilter {
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
    protected Boolean clear;

    public TransactionFilter() {
        this.uuid = UUID.randomUUID().toString();
        clear();
    }

    public void setup(LocalDateTime fromDate, LocalDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        clear = false;
    }

    public void clear() {
        clear = true;
    }

    public String getFromDateField() {
        return fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getToDateField() {
        return toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
