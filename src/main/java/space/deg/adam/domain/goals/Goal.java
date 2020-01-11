package space.deg.adam.domain.goals;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
public class Goal {
    @Id
    @Column(length = 100)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private String title;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date date;

    @Column(precision = 16, scale = 2)
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String currency;

    @NonNull
    private String status;
    private String image;
    private String url;

    @NonNull
    private String category;

    public Goal(User user, String title, Date date, BigDecimal amount, String status, String category) {
        this.uuid = UUID.randomUUID().toString();
        this.user = user;
        this.amount = amount;
        this.date = date;
        this.title = title;
        this.status = status;
        this.category = category;
    }

    public Goal(User user, String title, String description, Date date, BigDecimal amount, String currency, String status, String url, String category) {
        this.uuid = UUID.randomUUID().toString();
        this.user = user;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
        this.url = url;
        this.category = category;
    }

    public String getDateString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public String getDateField() {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getAmountString() {
        return amount.setScale(2, RoundingMode.HALF_UP).toString().replaceAll(" ", "");
    }
}
