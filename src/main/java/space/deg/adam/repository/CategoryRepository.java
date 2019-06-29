package space.deg.adam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.deg.adam.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Object> {
    Category findByName(String name);
}
