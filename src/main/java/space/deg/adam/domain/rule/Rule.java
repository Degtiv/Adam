package space.deg.adam.domain.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.transaction.TransactionType;
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
@Table(name = "rules")
@Data
@EqualsAndHashCode(exclude = { "transactions"})
@ToString(exclude = { "transactions"})
public class Rule {
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
    private TransactionType transactionType;

    @NonNull
    private Category category;

    @NonNull
    private RuleStrategy ruleStrategy;
    private String ruleParameter;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    public void setAmount (BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            transactionType = TransactionType.COST;
            amount = amount.negate();
        }

        this.amount = amount;
    }

    public Rule() {
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
    public String getRuleStrategyString() {
        return ruleStrategy.getTitle();
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

    public static RuleBuilder builder() {
        return new RuleBuilder();
    }

    public static class RuleBuilder {
        private User user;
        private String title = "No title";
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private BigDecimal amount = BigDecimal.ZERO;
        private String currency = "RUR";
        private TransactionType transactionType;
        private Category category = Category.BASE;
        private RuleStrategy ruleStrategy;
        private String ruleParameter;

        public RuleBuilder user(User user) {
            this.user = user;
            return this;
        }

        public RuleBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RuleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RuleBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public RuleBuilder startDate(String dateText) {
            startDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            return this;
        }

        public RuleBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public RuleBuilder endDate(String dateText) {
            endDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            return this;
        }

        public RuleBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public RuleBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public RuleBuilder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public RuleBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public RuleBuilder ruleStrategy(RuleStrategy ruleStrategy) {
            this.ruleStrategy = ruleStrategy;
            return this;
        }

        public RuleBuilder ruleParameter(String ruleParameter) {
            this.ruleParameter = ruleParameter;
            return this;
        }

        public Rule build() {
            Rule rule = new Rule();
            rule.setTransactionType(transactionType);
            rule.setUser(user);
            rule.setDescription(description);
            rule.setTitle(title);
            rule.setStartDate(startDate);
            rule.setEndDate(endDate);
            rule.setAmount(amount);
            rule.setCurrency(currency);
            rule.setCategory(category);
            rule.setRuleStrategy(ruleStrategy);
            rule.setRuleParameter(ruleParameter);
            return rule;
        }
    }
}
