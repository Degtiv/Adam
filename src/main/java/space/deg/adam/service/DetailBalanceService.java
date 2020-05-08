package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.balance.DetailBalance;
import space.deg.adam.domain.transaction.goals.Goal;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.MilestoneRepository;
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
    private static final long SECONDS_IN_DAY = 86_400;
    private static final long ONE_MILLION = 1_000_000;
    private static final long MICRO_SECONDS_IN_DAY = SECONDS_IN_DAY * ONE_MILLION;
    @Autowired
    private MilestoneService milestoneService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private MilestoneRepository milestoneRepository;
    @Autowired
    private GoalRepository goalRepository;

    public DetailBalance getDetailBalance(User user, LocalDateTime start, LocalDateTime end) {
        DetailBalance detailBalance = new DetailBalance();
        detailBalance.setStart(start.toLocalDate());
        detailBalance.setEnd(end.toLocalDate());
        detailBalance.setNow(LocalDateTime.now().toLocalDate());

        BigDecimal endOfPreviousDayBalance = milestoneService.getLastBalanceToDate(user, start);
        LocalDateTime iteratorDateTime = start.with(FirstSecondOfMonth.adjust());
        while (!iteratorDateTime.isAfter(end)) {
            if (iteratorDateTime.isEqual(start) || iteratorDateTime.isEqual(end) ||
                    iteratorDateTime.isAfter(start) && iteratorDateTime.isBefore(end)) {
                DetailBalance.DayReport dayReport = new DetailBalance.DayReport();
                dayReport.setDateTime(iteratorDateTime.toLocalDate());
                dayReport.setStartDayBalance(endOfPreviousDayBalance);
                dayReport.setEndDayBalance(endOfPreviousDayBalance);
                getTransactions(user, iteratorDateTime).forEach(dayReport::addTransactionToDayReport);
                getGoals(user, iteratorDateTime).forEach(dayReport::addGoalToDayReport);
                detailBalance.addDayReport(dayReport);

                endOfPreviousDayBalance = dayReport.getEndDayBalance();
            }

            iteratorDateTime = iteratorDateTime.plusDays(1);
        }
        System.out.println("exit detailBalance " + detailBalance);
        return detailBalance;
    }

    private List<Goal> getGoals(User user, LocalDateTime iteratorDateTime) {
        List<Goal> goals = new ArrayList<>();
        goalRepository
                .findByUserAndDateAfterAndDateBefore(user,
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, MICRO_SECONDS_IN_DAY - 1L).minusDays(1),
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, 0).plusDays(1),
                        Sort.by(Sort.Direction.DESC, "amount")).forEach(goals::add);
        return goals;
    }

    private List<Transaction> getTransactions(User user, LocalDateTime iteratorDateTime) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository
                .findByUserAndDateAfterAndDateBefore(user,
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, MICRO_SECONDS_IN_DAY - 1L).minusDays(1),
                        iteratorDateTime.with(ChronoField.MICRO_OF_DAY, 0).plusDays(1),
                        Sort.by(Sort.Direction.DESC, "amount")).forEach(transactions::add);
        return transactions;
    }
}
