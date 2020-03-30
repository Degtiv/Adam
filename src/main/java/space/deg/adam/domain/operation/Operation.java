package space.deg.adam.domain.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.operation.operationrule.OperationRule;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "operations")
@Data
@EqualsAndHashCode(exclude = { "transactions"})
@ToString(exclude = { "transactions"})
public class Operation {
    @Id
    @Column(length = 100)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @NonNull
    private String title;
    private String description;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    @NonNull
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    @NonNull
    private LocalDateTime endDate;

    @Column(precision = 16, scale = 2)
    @NonNull
    private BigDecimal amount;

    @NonNull
    private String currency;

    @NonNull
    private Category category;

    @NonNull
    private OperationRule rule;
    private String ruleParameter;

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    public Operation() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void setStartDate(String dateText) {
        startDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String dateText) {
        startDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @JsonIgnore
    public String getRuleString() {
        return rule.getTitle();
    }

    @JsonIgnore
    public String getStartDateString() {
        return startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @JsonIgnore
    public String getStartDateField() {
        return startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @JsonIgnore
    public String getEndDateString() {
        return endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @JsonIgnore
    public String getEndDateField() {
        return endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getAmountString() {
        return amount.setScale(2, RoundingMode.HALF_UP).toString().replaceAll(" ", "");
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public static OperationBuilder builder() {
        return new OperationBuilder();
    }

    public static class OperationBuilder {
        private User user;
        private String title = "No title";
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private BigDecimal amount = BigDecimal.ZERO;
        private String currency = "RUR";
        private Category category = Category.BASE;
        private OperationRule rule;
        private String ruleParameter;

        public OperationBuilder user(User user) {
            this.user = user;
            return this;
        }

        public OperationBuilder title(String title) {
            this.title = title;
            return this;
        }

        public OperationBuilder description(String description) {
            this.description = description;
            return this;
        }

        public OperationBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public OperationBuilder startDate(String dateText) {
            startDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            return this;
        }

        public OperationBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public OperationBuilder endDate(String dateText) {
            endDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            return this;
        }

        public OperationBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public OperationBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public OperationBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public OperationBuilder rule(OperationRule rule) {
            this.rule = rule;
            return this;
        }

        public OperationBuilder ruleParameter(String ruleParameter) {
            this.ruleParameter = ruleParameter;
            return this;
        }

        public Operation build() {
            Operation operation = new Operation();
            operation.setUser(user);
            operation.setDescription(description);
            operation.setTitle(title);
            operation.setStartDate(startDate);
            operation.setEndDate(endDate);
            operation.setAmount(amount);
            operation.setCurrency(currency);
            operation.setCategory(category);
            operation.setRule(rule);
            operation.setRuleParameter(ruleParameter);
            return operation;
        }
    }
}
