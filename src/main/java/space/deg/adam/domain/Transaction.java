package space.deg.adam.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @Column(length = 100)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(precision = 16, scale = 2)
    private BigDecimal amount;
    private String currency;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String title;
    private String description;
    private String status;
    private String category;

    public Transaction(User user, BigDecimal amount, String currency, Date date, String title, String description, String status, String category) {
        this.uuid = UUID.randomUUID().toString();
        this.user = user;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
        this.category = category;
    }

    public String getDateString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }
}
