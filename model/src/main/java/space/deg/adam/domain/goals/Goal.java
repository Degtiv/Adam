package space.deg.adam.domain.goals;

import lombok.Data;
import space.deg.adam.domain.transaction.Transaction;

import javax.persistence.*;

@Entity
@Table(name = "goals")
@Data
public class Goal {
    @Id
    @Column(length = 100)
    protected String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    private String image;
    private String url;

    public Goal() {
        super();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Goal goal = new Goal();
        private String image;
        private String url;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }


        protected void fillFields(Goal goal) {
            goal.setUrl(url);
            goal.setImage(image);
        }

        public Goal build() {
            fillFields(goal);
            return goal;
        }
    }
}
