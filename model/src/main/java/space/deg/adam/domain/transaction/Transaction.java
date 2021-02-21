package space.deg.adam.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @Column(length = 100)
    protected String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    protected User user;

    @NonNull
    protected String title;
    protected String description;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @NonNull
    protected LocalDateTime date;

    @Column(precision = 16, scale = 2)
    @NonNull
    protected BigDecimal amount;

    @NonNull
    protected String currency;

    @NonNull
    protected Status status;

    public boolean isAct() {
        return status == Status.CONFIRMED || !date.isBefore(LocalDateTime.now());
    }

    public void setDate(String dateText) {
        date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Transaction() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Transaction(Transaction transaction) {
        this();
        this.user = transaction.user;
        this.title = transaction.title;
        this.description = transaction.description;
        this.date = transaction.date;
        this.amount = transaction.amount;
        this.currency = transaction.currency;
        this.status = transaction.status;
    }
}
