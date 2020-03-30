package space.deg.adam.domain.operation.operationrule;

import space.deg.adam.domain.operation.Operation;
import space.deg.adam.service.TransactionService;

public interface OperationRuleStrategy {
    String getTitle();
    void generateTransactions(Operation operation);
    void setTransactionService(TransactionService transactionService);
}
