package space.deg.adam.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.NonNull;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @Column(length = 100)
    protected String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    protected User user;

    @NonNull
    @Column(unique = true)
    protected String title;
    protected String description;

    @NonNull
    protected String currency;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Account(User user, String title, String description, String currency) {
        this();
        this.user = user;
        this.title = title;
        this.description = description;
        this.currency = currency;
    }

}
