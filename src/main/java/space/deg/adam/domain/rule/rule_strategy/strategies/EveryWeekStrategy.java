package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class EveryWeekStrategy extends AbstractStrategy{
    public EveryWeekStrategy() {
        super.ruleStrategy = RuleStrategy.EVERY_WEEK;
    }

    @Override
    public void generateTransactions(Rule rule, Transaction referenceTransaction) {
        LocalDateTime iteratorDateTime = rule.getStartDate();
        LocalDateTime end = rule.getEndDate();
        DayOfWeek parameterDay = DayOfWeek.valueOf(rule.getRuleParameter());

        if (iteratorDateTime.getDayOfWeek() != parameterDay)
            iteratorDateTime = iteratorDateTime.with(TemporalAdjusters.next(parameterDay));

        while (!iteratorDateTime.isAfter(end)) {
            Transaction transaction = new Transaction(referenceTransaction);
            transaction.setDate(iteratorDateTime);

            transaction.setRule(rule);
            transactionService.addTransaction(transaction);
            rule.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.with(TemporalAdjusters.next(parameterDay));
        }
    }
}
