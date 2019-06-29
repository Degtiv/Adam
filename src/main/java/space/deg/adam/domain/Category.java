package space.deg.adam.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    private String color = "122FAA";

    //TODO: add 'ON DELETE SET NULL'
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "c_sectionID")
    private Section section;

    public Category(String name,
                    String description,
                    String color,
                    Section section) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.section = section;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
