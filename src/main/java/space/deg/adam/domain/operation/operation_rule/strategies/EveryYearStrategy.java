package space.deg.adam.domain.operation.operation_rule.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operation_rule.OperationRule;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EveryYearStrategy extends AbstractStrategy{
    public EveryYearStrategy() {
        super.operationRule = OperationRule.EVERY_YEAR;
    }

    @Override
    public void generateTransactions(Operation operation) {
        LocalDateTime iteratorDateTime = operation.getStartDate();
        LocalDateTime end = operation.getEndDate();
        LocalDateTime parameterDay = LocalDateTime.parse(operation.getRuleParameter(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        if (iteratorDateTime.isAfter(parameterDay))
            iteratorDateTime = iteratorDateTime.plusYears(1);

        while (!iteratorDateTime.isAfter(end)) {
            Transaction transaction = ((Transaction.Builder) Transaction.builder()
                    .user(operation.getUser())
                    .title(operation.getTitle())
                    .date(iteratorDateTime)
                    .amount(operation.getAmount())
                    .currency("RUR")
                    .transactionType(operation.getTransactionType())
                    .description(operation.getDescription())
                    .status(Status.PLANNED))
                    .category(operation.getCategory())
                    .build();

            transaction.setOperation(operation);
            transactionService.addTransaction(transaction);
            operation.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.plusYears(1);
        }
    }
}
