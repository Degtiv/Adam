package space.deg.adam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_categories")
public class CategoryOld {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Column(length = 1000)
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String color = "122FAA";

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "c_sectionID")
    @Getter
    @Setter
    private Section section;

    public CategoryOld(String name,
                       String description,
                       String color,
                       Section section) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.section = section;
    }

    public CategoryOld() {
    }
}
