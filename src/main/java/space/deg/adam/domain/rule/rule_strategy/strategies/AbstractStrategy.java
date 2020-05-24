package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.service.TransactionService;

public class AbstractStrategy {
    protected RuleStrategy ruleStrategy;
    //todo: try with @autowired
    protected TransactionService transactionService;

    public String getTitle() {
        return ruleStrategy.getTitle();
    }

    public void generateTransactions(Rule rule, Transaction referenceTransaction) {
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
