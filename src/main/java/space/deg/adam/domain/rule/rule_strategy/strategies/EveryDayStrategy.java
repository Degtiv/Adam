package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.BaseTransaction;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;

public class EveryDayStrategy extends AbstractStrategy {
    public EveryDayStrategy() {
        super.ruleStrategy = RuleStrategy.EVERY_DAY;
    }

    @Override
    public void generateTransactions(Rule rule, Transaction referenceTransaction) {
        LocalDateTime iteratorDateTime = rule.getStartDate();
        LocalDateTime end = rule.getEndDate();

        while (!iteratorDateTime.isAfter(end)) {
            Transaction transaction = new Transaction(referenceTransaction);
            transaction.setDate(iteratorDateTime);

            transactionService.addTransaction(transaction);
            rule.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.plusDays(1);
        }
    }
}
