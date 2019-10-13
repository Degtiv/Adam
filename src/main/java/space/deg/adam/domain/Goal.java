package space.deg.adam.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private String title;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(precision = 16, scale = 2)
    private BigDecimal amount;
    private String currency;

    private String status;
    private String pictureUrl;
    private String url;

    public Goal(User user, String title, String description, Date date, BigDecimal amount, String currency, String status, String pictureUrl, String url) {
        this.uuid = UUID.randomUUID().toString();
        this.user = user;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public String getDateString() {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
