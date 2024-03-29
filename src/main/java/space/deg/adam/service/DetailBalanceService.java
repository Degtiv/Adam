package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.balance.DetailBalance;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.transaction.BaseTransaction;
import space.deg.adam.domain.transaction.TransactionType;
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

import static space.deg.adam.constants.CommonConstants.MICRO_SECONDS_IN_DAY;

@Service
public class DetailBalanceService {
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

        BigDecimal endOfPreviousDayBalance = milestoneService.getLastAmountOfMilestoneToDate(user, start);
        LocalDateTime iteratorDateTime = start.with(ChronoField.DAY_OF_MONTH, 1).with(ChronoField.MICRO_OF_DAY, 0);
        while (!iteratorDateTime.isAfter(end)) {
            DetailBalance.DayReport dayReport = new DetailBalance.DayReport();
            dayReport.setDateTime(iteratorDateTime.toLocalDate());
            dayReport.setStartDayBalance(endOfPreviousDayBalance);
            dayReport.setEndDayBalance(endOfPreviousDayBalance);
            getTransactions(user, iteratorDateTime).forEach(t -> {
                dayReport.addTransactionToDayReport(t);
                if (t.isAct())
                    dayReport.addToEndDayBalance(t.getGrantedAmount());
            });
            getGoals(user, iteratorDateTime).forEach(t -> {
                dayReport.addGoalToDayReport(t);
                if (t.isAct())
                    dayReport.addToEndDayBalance(t.getGrantedAmount());
            });
            endOfPreviousDayBalance = dayReport.getEndDayBalance();

            if (iteratorDateTime.isEqual(start) || iteratorDateTime.isEqual(end) ||
                    iteratorDateTime.isAfter(start) && iteratorDateTime.isBefore(end)) {
                detailBalance.addDayReport(dayReport);
            }

            iteratorDateTime = iteratorDateTime.plusDays(1);
        }
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
