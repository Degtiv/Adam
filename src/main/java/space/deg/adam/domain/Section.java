package space.deg.adam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_sections")
public class Section {
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
    private String color = "0bda51";

    public Section(String name,
                   String description,
                   String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public Section() {
    }

}
