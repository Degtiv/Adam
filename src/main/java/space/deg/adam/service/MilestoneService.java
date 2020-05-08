package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.transaction.TransactionType;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.MilestoneRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MilestoneService {
    @Autowired
    private MilestoneRepository milestoneRepository;

    public void addTransactionToMilestones(User user, Transaction transaction) {
        milestoneRepository.findByUserAndDateAfter(user, transaction.getDate()).forEach(milestone -> {
            if (transaction.getTransactionType() == TransactionType.INCOME)
                milestone.increaseAmount(transaction.getAmount());
            if (transaction.getTransactionType() == TransactionType.COST)
                milestone.decreaseAmount(transaction.getAmount());

            milestoneRepository.save(milestone);
        });
    }

    public void removeTransactionFromMilestones(User user, Transaction transaction) {
        milestoneRepository.findByUserAndDateAfter(user, transaction.getDate()).forEach(balance -> {
            if (transaction.getTransactionType() == TransactionType.INCOME)
                balance.decreaseAmount(transaction.getAmount());
            if (transaction.getTransactionType() == TransactionType.COST)
                balance.increaseAmount(transaction.getAmount());

            milestoneRepository.save(balance);
        });
    }

    public BigDecimal getLastBalanceToDate(User user, LocalDateTime dateTime) {
        return milestoneRepository
                .findByUserAndDateBefore(user, dateTime, Sort.by(Sort.Direction.DESC, "date"))
                .iterator()
                .next()
                .getAmount();
    }
}