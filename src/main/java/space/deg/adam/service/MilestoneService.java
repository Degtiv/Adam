package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.transaction.BaseTransaction;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.transaction.TransactionType;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.MilestoneRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Service
public class MilestoneService {
    @Autowired
    private MilestoneRepository milestoneRepository;

    public void addTransactionToMilestones(User user, BaseTransaction baseTransaction) {
        milestoneRepository.findByUserAndDateAfter(user, baseTransaction.getDate()).forEach(milestone -> {
            if (baseTransaction.getTransactionType() == TransactionType.INCOME)
                milestone.increaseAmount(baseTransaction.getAmount());
            if (baseTransaction.getTransactionType() == TransactionType.COST)
                milestone.decreaseAmount(baseTransaction.getAmount());

            milestoneRepository.save(milestone);
        });
    }

    public void removeTransactionFromMilestones(User user, BaseTransaction baseTransaction) {
        milestoneRepository.findByUserAndDateAfter(user, baseTransaction.getDate()).forEach(balance -> {
            if (baseTransaction.getTransactionType() == TransactionType.INCOME)
                balance.decreaseAmount(baseTransaction.getAmount());
            if (baseTransaction.getTransactionType() == TransactionType.COST)
                balance.increaseAmount(baseTransaction.getAmount());

            milestoneRepository.save(balance);
        });
    }

    public BigDecimal getLastAmountOfMilestoneToDate(User user, LocalDateTime dateTime) {
        LocalDateTime firstDayOfMonth = dateTime.with(ChronoField.DAY_OF_MONTH, 1).with(ChronoField.MICRO_OF_DAY, 0);
        return milestoneRepository
                .findByUserAndDate(user, firstDayOfMonth, Sort.by(Sort.Direction.DESC, "date"))
                .iterator()
                .next()
                .getAmount();
    }
}
