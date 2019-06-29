package space.deg.adam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.deg.adam.domain.Section;

public interface SectionRepository extends JpaRepository<Section, Object> {
    Section findByName(String name);
}
