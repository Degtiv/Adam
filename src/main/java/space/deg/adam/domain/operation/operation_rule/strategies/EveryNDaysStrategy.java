package space.deg.adam.domain.operation.operation_rule.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operation_rule.OperationRule;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;

public class EveryNDaysStrategy extends AbstractStrategy{
    public EveryNDaysStrategy() {
        super.operationRule = OperationRule.EVERY_N_DAYS;
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