package space.deg.adam.domain.balance;

import com.sun.tools.javac.util.List;
import lombok.Data;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DetailBalance {
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime now;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private List<DayReport> operations;

    @Data
    private class DayReport {
        LocalDateTime dateTime;
        BigDecimal dayBalance;
        List<Transaction> transactions;
        List<Goal> goals;
    }
}
