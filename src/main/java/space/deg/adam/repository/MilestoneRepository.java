package space.deg.adam.repository;

import org.springframework.data.repository.CrudRepository;
import space.deg.adam.domain.Milestone;

public interface MilestoneRepository extends CrudRepository<Milestone, Object> {
    Milestone findById(Long id);
}
