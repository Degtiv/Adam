package space.deg.adam.domain.balance;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "balances")
@Data
@NoArgsConstructor
public class Balance {
    @Id
    @Column(length = 100)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date date;

    @Column(precision = 16, scale = 2)
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String currency;

    public Balance(User user, Date date, BigDecimal amount, String currency) {
        this.uuid = UUID.randomUUID().toString();
        this.user = user;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
    }
}
