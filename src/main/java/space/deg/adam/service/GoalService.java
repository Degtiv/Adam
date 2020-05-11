package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.transaction.goals.Goal;
import space.deg.adam.repository.GoalRepository;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MilestoneService milestoneService;

    public void addGoal(Goal goal) {
        goalRepository.save(goal);
        milestoneService.addTransactionToMilestones(goal.getUser(), goal);
    }

    public void deleteGoal(Goal goal) {
        milestoneService.removeTransactionFromMilestones(goal.getUser(), goal);
        goalRepository.delete(goal);
    }
}
