package space.deg.adam.domain.balance;

import lombok.Data;
import space.deg.adam.domain.transaction.goals.Goal;
import space.deg.adam.domain.transaction.Transaction;

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
        private BigDecimal dayBalance;
        private List<Transaction> transactions;
        private List<Goal> goals;
    }
}
