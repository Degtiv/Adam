package space.deg.adam.domain.operation.operation_rule.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operation_rule.OperationRule;
import space.deg.adam.domain.transaction.Transaction;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class EveryWeekStrategy extends AbstractStrategy{
    public EveryWeekStrategy() {
        super.operationRule = OperationRule.EVERY_WEEK;
    }

    @Override
    public void generateTransactions(Operation operation) {
        LocalDateTime iteratorDateTime = operation.getStartDate();
        LocalDateTime end = operation.getEndDate();
        DayOfWeek parameterDay = DayOfWeek.valueOf(operation.getRuleParameter());

        if (iteratorDateTime.getDayOfWeek() != parameterDay)
            iteratorDateTime = iteratorDateTime.with(TemporalAdjusters.next(parameterDay));

        while (!iteratorDateTime.isAfter(end)) {
            Transaction transaction = ((Transaction.Builder)Transaction.builder()
                    .user(operation.getUser())
                    .title(operation.getTitle())
                    .date(iteratorDateTime)
                    .amount(operation.getAmount())
                    .currency("RUR")
                    .description(operation.getDescription())
                    .status(Status.PLANNED))
                    .category(operation.getCategory())
                    .build();

            transaction.setOperation(operation);
            transactionService.addTransaction(transaction);
            operation.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.with(TemporalAdjusters.next(parameterDay));
        }
    }
}
