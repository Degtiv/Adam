package space.deg.adam.domain.balance;

import lombok.Data;
import space.deg.adam.domain.transaction.BaseTransaction;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.transaction.TransactionType;
import space.deg.adam.domain.transaction.goals.Goal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DetailBalance {
    private LocalDate start;
    private LocalDate end;
    private LocalDate now;
    private List<DayReport> dayReports = new ArrayList();

    public void addDayReport(DayReport dayReport) {
        dayReports.add(dayReport);
    }

    @Data
    public static class DayReport {
        private LocalDate dateTime;
        private BigDecimal startDayBalance = BigDecimal.ZERO;
        private BigDecimal endDayBalance = BigDecimal.ZERO;
        private List<Transaction> transactions = new ArrayList<>();
        private List<Goal> goals = new ArrayList<>();

        public void addTransactionToDayReport(Transaction transaction) {
            transactions.add(transaction);
            addBaseTransactionToDayBalance(transaction);
        }

        public void addGoalToDayReport(Goal goal) {
            goals.add(goal);
            addBaseTransactionToDayBalance(goal);
        }

        private void addBaseTransactionToDayBalance(BaseTransaction baseTransaction) {
            BigDecimal increaseAmount = BigDecimal.ZERO;
            if (baseTransaction.getTransactionType() == TransactionType.INCOME)
                increaseAmount = baseTransaction.getAmount();
            if (baseTransaction.getTransactionType() == TransactionType.COST)
                increaseAmount = baseTransaction.getAmount().negate();
            endDayBalance = endDayBalance.add(increaseAmount);
        }
    }
}
