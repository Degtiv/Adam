package space.deg.adam.domain.transaction;

import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.operation.Operation;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Transaction extends BaseTransaction {
    @NonNull
    protected Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    protected Operation operation;

    public Transaction() {
        super();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseTransaction.Builder {
        Transaction transaction = new Transaction();
        private Category category = Category.BASE;

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        protected void fillFields(Transaction transaction) {
            super.fillFields(transaction);
            transaction.setCategory(category);
        }

        public Transaction build() {
            fillFields(transaction);
            return transaction;
        }
    }
}
