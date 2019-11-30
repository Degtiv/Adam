package space.deg.adam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.deg.adam.domain.CategoryOld;

public interface CategoryRepository extends JpaRepository<CategoryOld, Object> {
    CategoryOld findByName(String name);
}
