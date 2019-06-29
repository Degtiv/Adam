package space.deg.adam.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_milstones")
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    @Column(precision = 16, scale = 2)
    private BigDecimal bigDecimal;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_prevMSID")
    private Milestone previousMilestone;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_nextMSID")
    private Milestone nextMilestone;

    public Milestone(@NotNull Date date,
                     @NotNull BigDecimal bigDecimal,
                     Milestone previousMilestone,
                     Milestone nextMilestone) {
        this.date = date;
        this.bigDecimal = bigDecimal;
        this.previousMilestone = previousMilestone;
        this.nextMilestone = nextMilestone;
    }

    public Milestone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Milestone getPreviousMilestone() {
        return previousMilestone;
    }

    public void setPreviousMilestone(Milestone previousMilestone) {
        this.previousMilestone = previousMilestone;
    }

    public Milestone getNextMilestone() {
        return nextMilestone;
    }

    public void setNextMilestone(Milestone nextMilestone) {
        this.nextMilestone = nextMilestone;
    }
}
