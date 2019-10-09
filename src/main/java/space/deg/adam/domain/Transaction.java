package space.deg.adam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO: write like it should be
@Entity
@Table(name = "t_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date date;

    @NotNull
    @Column(precision = 16, scale = 2)
    @Getter
    @Setter
    private BigDecimal value = BigDecimal.ZERO;

    @Column(length = 1000)
    @Getter
    @Setter
    private String description;

    @NotNull
    @Column(columnDefinition = "TINYINT UNSIGNED")
    @Getter
    @Setter
    private Integer status = 0;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_milestioneID")
    @Getter
    @Setter
    private Milestone milestone;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_categoryID")
    @Getter
    @Setter
    private Category category;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_UserID")
    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private String image;

    public Transaction(){}


    public Transaction(@NotNull String name,
                       @NotNull Date date,
                       @NotNull BigDecimal value,
                       String description,
                       @NotNull Integer status,
                       Milestone milestone,
                       Category category,
                       User user) {
        this.name = name;
        this.date = date;
        this.value = value;
        this.description = description;
        this.status = status;
        this.milestone = milestone;
        this.category = category;
        this.user = user;
    }

    public String getUserName() {
        if (user != null)
            return user.getUsername();
        return "<None>";
    }

    public String getDateString() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyy");
        return format.format(date);
    }

    public String getMilestoneId() {
        if (milestone != null)
            return milestone.getId().toString();
        else
            return "null";
    }

    public String getCategoryName() {
        if (category != null)
            return category.getName();
        else
            return "null";
    }
}
