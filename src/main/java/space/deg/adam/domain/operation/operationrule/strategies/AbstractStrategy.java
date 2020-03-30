package space.deg.adam.domain.operation.operationrule.strategies;

import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operationrule.OperationRule;
import space.deg.adam.domain.operation.operationrule.OperationRuleStrategy;
import space.deg.adam.service.TransactionService;

public class AbstractStrategy implements OperationRuleStrategy {
    protected OperationRule operationRule;
    protected TransactionService transactionService;

    @Override
    public String getTitle() {
        return operationRule.getTitle();
    }

    @Override
    public void generateTransactions(Operation operation) {
    }

    @Override
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
