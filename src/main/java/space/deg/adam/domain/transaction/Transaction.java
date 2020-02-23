package space.deg.adam.domain.transaction;

import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private String title;
    private String description;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @NonNull
    private LocalDateTime date;

    @Column(precision = 16, scale = 2)
    @NonNull
    private BigDecimal amount;

    @NonNull
    private String currency;

    @NonNull
    private Status status;

    @NonNull
    private Category category;

    public Transaction() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void setDate(String dateText) {
        date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getDateField() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getAmountString() {
        return amount.setScale(2, RoundingMode.HALF_UP).toString().replaceAll(" ", "");
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public static class TransactionBuilder {
        private User user;
        private String title = "No title";
        private String description;
        private LocalDateTime date;
        private BigDecimal amount = BigDecimal.ZERO;
        private String currency = "RUR";
        private Status status = Status.PLANNED;
        private Category category = Category.BASE;

        public TransactionBuilder user(User user) {
            this.user = user;
            return this;
        }

        public TransactionBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TransactionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public TransactionBuilder date(String dateText) {
            date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            return this;
        }

        public TransactionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public TransactionBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setDescription(description);
            transaction.setTitle(title);
            transaction.setDate(date);
            transaction.setAmount(amount);
            transaction.setCurrency(currency);
            transaction.setStatus(status);
            transaction.setCategory(category);
            return transaction;
        }
    }
}
