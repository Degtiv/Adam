package space.deg.adam.domain.balance;

import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "balances")
@Data
public class Balance {
    @Id
    @Column(length = 100)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @NonNull
    private LocalDateTime date;

    @Column(precision = 16, scale = 2)
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String currency;

    public Balance(){
        this.uuid = UUID.randomUUID().toString();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String dateText) {
        date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getDateField() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private User user;
        private LocalDateTime date;
        private BigDecimal amount = BigDecimal.ZERO;
        private String currency = "RUR";

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder date(String dateText) {
            date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Balance build() {
            Balance balance = new Balance();
            balance.setUser(user);
            balance.setDate(date);
            balance.setAmount(amount);
            balance.setCurrency(currency);
            return balance;
        }
    }
}
