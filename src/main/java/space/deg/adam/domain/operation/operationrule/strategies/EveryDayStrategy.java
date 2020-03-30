package space.deg.adam.domain.operation.operationrule.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operationrule.OperationRule;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;

public class EveryDayStrategy extends AbstractStrategy {
    public EveryDayStrategy() {
        super.operationRule = OperationRule.EVERY_DAY;
    }

    @Override
    public void generateTransactions(Operation operation) {
        LocalDateTime iteratorDateTime = operation.getStartDate();
        LocalDateTime end = operation.getEndDate();

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
            iteratorDateTime = iteratorDateTime.plusDays(1);
        }
    }
}
