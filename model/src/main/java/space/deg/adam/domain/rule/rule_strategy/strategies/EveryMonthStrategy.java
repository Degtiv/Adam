package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;

public class EveryMonthStrategy extends AbstractStrategy {
    public EveryMonthStrategy() {
        super.ruleStrategy = RuleStrategy.EVERY_MONTH;
    }

    @Override
    public void generateTransactions(Rule rule, Transaction referenceTransaction) {
        LocalDateTime iteratorDateTime = rule.getStartDate();
        LocalDateTime end = rule.getEndDate();
        int parameterDay = Integer.valueOf(rule.getRuleParameter());

        if (iteratorDateTime.getDayOfMonth() > parameterDay)
            iteratorDateTime = iteratorDateTime.plusMonths(1);

        iteratorDateTime = iteratorDateTime.withDayOfMonth(parameterDay);

        while (!iteratorDateTime.isAfter(end)) {
            Transaction transaction = new Transaction(referenceTransaction);
            transaction.setDate(iteratorDateTime);

            transactionService.addTransaction(transaction);
            rule.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.plusMonths(1);
        }
    }
}
