package space.deg.adam.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.NonNull;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class BaseTransaction {
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
    protected TransactionType transactionType;

    @NonNull
    protected Status status;

    public void setDate(String dateText) {
        date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonIgnore
    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @JsonIgnore
    public String getDateField() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getAmountString() {
        return amount.setScale(2, RoundingMode.HALF_UP).toString().replaceAll(" ", "");
    }


    public BaseTransaction() {
        this.uuid = UUID.randomUUID().toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        BaseTransaction baseTransaction = new BaseTransaction();
        protected User user;
        protected String title = "No title";
        protected String description;
        protected LocalDateTime date;
        protected BigDecimal amount = BigDecimal.ZERO;
        protected String currency = "RUR";
        protected TransactionType transactionType = TransactionType.INCOME;
        protected Status status = Status.PLANNED;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
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

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        protected void fillFields(BaseTransaction baseTransaction) {
            baseTransaction.setUser(user);
            baseTransaction.setDescription(description);
            baseTransaction.setTitle(title);
            baseTransaction.setDate(date);
            baseTransaction.setAmount(amount);
            baseTransaction.setCurrency(currency);
            baseTransaction.setTransactionType(transactionType);
            baseTransaction.setStatus(status);
        }

        public BaseTransaction build() {
            fillFields(baseTransaction);
            return baseTransaction;
        }
    }
}
