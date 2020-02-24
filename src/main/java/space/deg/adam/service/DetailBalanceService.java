package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.balance.DetailBalance;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.BalanceRepository;
import space.deg.adam.repository.GoalRepository;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.utils.FirstSecondOfMonth;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetailBalanceService {
    private static final int SECONDS_IN_DAY = 86400;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private GoalRepository goalRepository;

    public DetailBalance getDetailBalance(User user, LocalDateTime start, LocalDateTime end) {
        DetailBalance detailBalance = new DetailBalance();
        detailBalance.setStart(start);
        detailBalance.setEnd(end);
        detailBalance.setNow(LocalDateTime.now());

        BigDecimal endOfDayBalance = balanceService.getLastBalanceToDate(user, start);
        LocalDateTime iteratorDateTime = start.with(FirstSecondOfMonth.adjust());
        BigDecimal minAmount = BigDecimal.ZERO;
        BigDecimal maxAmount = BigDecimal.ZERO;
        while (iteratorDateTime.isBefore(end)) {
            DetailBalance.DayReport dayReport = new DetailBalance.DayReport();

            List<Transaction> transactions = getTransactions(user, iteratorDateTime);
            endOfDayBalance = operateTransactions(endOfDayBalance, transactions);
            for (Transaction transaction : transactions) {
                if (transaction.getAmount().compareTo(minAmount) < 0)
                    minAmount = transaction.getAmount();
                if (transaction.getAmount().compareTo(maxAmount) > 0)
                    maxAmount = transaction.getAmount();
            }

            List<Goal> goals = getGoals(user, iteratorDateTime);
            endOfDayBalance = operateGoals(endOfDayBalance, goals);
            for (Goal goal : goals) {
                if (goal.getAmount().compareTo(minAmount) < 0)
                    minAmount = goal.getAmount();
                if (goal.getAmount().compareTo(maxAmount) > 0)
                    maxAmount = goal.getAmount();
            }

            dayReport.setTransactions(transactions);
            dayReport.setGoals(goals);
            dayReport.setDateTime(iteratorDateTime);
            dayReport.setDayBalance(endOfDayBalance);
            detailBalance.addDayReport(dayReport);
            detailBalance.setMinAmount(minAmount);
            detailBalance.setMaxAmount(maxAmount);
            iteratorDateTime = iteratorDateTime.plusDays(1);
        }

        return detailBalance;
    }

    private BigDecimal operateGoals(BigDecimal endOfDayBalance, List<Goal> goals) {
        for (Goal goal : goals)
            endOfDayBalance = endOfDayBalance.add(goal.getAmount());
        return endOfDayBalance;
    }

    private BigDecimal operateTransactions(BigDecimal endOfDayBalance, List<Transaction> transactions) {
        for (Transaction transaction : transactions)
            endOfDayBalance = endOfDayBalance.add(transaction.getAmount());
        return endOfDayBalance;
    }

    private List<Goal> getGoals(User user, LocalDateTime iteratorDateTime) {
        List<Goal> goals = new ArrayList<>();
        goalRepository
                .findByUserAndDateAfterAndDateBefore(user,
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, 86400L * 1_000_000L - 1).minusDays(1),
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, 0).plusDays(1),
                        Sort.by(Sort.Direction.DESC, "amount")).forEach(goals::add);
        return goals;
    }

    private List<Transaction> getTransactions(User user, LocalDateTime iteratorDateTime) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository
                .findByUserAndDateAfterAndDateBefore(user,
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, 86400L * 1_000_000L - 1).minusDays(1),
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, 0).plusDays(1),
                        Sort.by(Sort.Direction.DESC, "amount")).forEach(transactions::add);
        return transactions;
    }
}
