package space.deg.adam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_milstones")
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date date;

    @NotNull
    @Column(precision = 16, scale = 2)
    @Getter
    @Setter
    private BigDecimal bigDecimal;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_prevMSID")
    @Getter
    @Setter
    private Milestone previousMilestone;

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_nextMSID")
    @Getter
    @Setter
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
}
