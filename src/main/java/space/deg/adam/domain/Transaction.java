package space.deg.adam.domain;

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
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    @Column(precision = 16, scale = 2)
    private BigDecimal value = BigDecimal.ZERO;

    @Column(length = 1000)
    private String description;

    @NotNull
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer status = 0;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_milestioneID")
    private Milestone milestone;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_categoryID")
    private Category category;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_UserID")
    private User user;



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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
