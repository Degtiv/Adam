package space.deg.adam.domain.transaction.goals;

import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.transaction.BaseTransaction;
import space.deg.adam.domain.transaction.TransactionType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "goals")
@Data
public class Goal extends BaseTransaction {
    @NonNull
    protected Category category;

    private String image;
    private String url;

    public Goal() {
        super();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseTransaction.Builder {
        Goal goal = new Goal();
        private String image;
        private String url;
        private Category category = Category.BASE;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        protected void fillFields(Goal goal) {
            super.fillFields(goal);
            goal.setTransactionType(TransactionType.COST);
            goal.setUrl(url);
            goal.setImage(image);
            goal.setCategory(category);
        }

        public Goal build() {
            fillFields(goal);
            return goal;
        }
    }
}
