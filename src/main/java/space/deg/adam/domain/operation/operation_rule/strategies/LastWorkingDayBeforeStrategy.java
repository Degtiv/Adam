package space.deg.adam.domain.operation.operation_rule.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operation_rule.OperationRule;
import space.deg.adam.domain.transaction.Transaction;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class LastWorkingDayBeforeStrategy extends AbstractStrategy{
    public LastWorkingDayBeforeStrategy() {
        super.operationRule = OperationRule.LAST_WORKING_DAY_BEFORE;
    }

    @Override
    public void generateTransactions(Operation operation) {
        LocalDateTime iteratorDateTime = operation.getStartDate();
        LocalDateTime end = operation.getEndDate();
        int parameterDay = Integer.valueOf(operation.getRuleParameter());

        if (iteratorDateTime.getDayOfMonth() > parameterDay)
            iteratorDateTime = iteratorDateTime.plusMonths(1);

        iteratorDateTime = iteratorDateTime.withDayOfMonth(parameterDay);

        while (!iteratorDateTime.isAfter(end)) {
            if (iteratorDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    iteratorDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
                iteratorDateTime.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

            Transaction transaction = Transaction.builder()
                    .user(operation.getUser())
                    .title(operation.getTitle())
                    .date(iteratorDateTime)
                    .amount(operation.getAmount())
                    .description(operation.getDescription())
                    .status(Status.PLANNED)
                    .category(operation.getCategory())
                    .currency("RUR")
                    .build();

            transaction.setOperation(operation);
            transactionService.addTransaction(transaction);
            operation.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.plusMonths(1);
        }
    }
}
