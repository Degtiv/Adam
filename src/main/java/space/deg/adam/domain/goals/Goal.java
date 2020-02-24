package space.deg.adam.domain.goals;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "goals")
@Data
public class Goal {
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

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @NonNull
    private LocalDateTime date;

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

    public Goal() {
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private User user;
        private String title = "No title";
        private String description;
        private LocalDateTime date;
        private BigDecimal amount = BigDecimal.ZERO;
        private String currency = "RUR";
        private String status = Status.PLANNED.getTitle();
        private String image;
        private String url;
        private String category = Category.BASE.getTitle();

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

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Goal build() {
            Goal goal = new Goal();
            goal.setUser(user);
            goal.setDescription(description);
            goal.setTitle(title);
            goal.setDate(date);
            goal.setAmount(amount);
            goal.setCurrency(currency);
            goal.setStatus(status);
            goal.setUrl(url);
            goal.setImage(image);
            goal.setCategory(category);
            return goal;
        }
    }
}
