package space.deg.adam.domain.balance;

import lombok.Data;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DetailBalance {
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime now;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private List<DayReport> dayReports = new ArrayList();

    public void addDayReport(DayReport dayReport) {
        dayReports.add(dayReport);
    }

    @Data
    public static class DayReport {
        private LocalDateTime dateTime;
        private BigDecimal dayBalance;
        private List<Transaction> transactions;
        private List<Goal> goals;
    }
}
