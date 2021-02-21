package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.repository.GoalRepository;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;


    public void addGoal(Goal goal) {
        goalRepository.save(goal);
    }

    public void deleteGoal(Goal goal) {
        goalRepository.delete(goal);
    }
}
