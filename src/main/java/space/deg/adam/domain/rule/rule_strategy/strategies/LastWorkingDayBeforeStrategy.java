package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class LastWorkingDayBeforeStrategy extends AbstractStrategy{
    public LastWorkingDayBeforeStrategy() {
        super.ruleStrategy = RuleStrategy.LAST_WORKING_DAY_BEFORE;
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
            if (iteratorDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    iteratorDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
                iteratorDateTime = iteratorDateTime.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

            Transaction transaction = new Transaction(referenceTransaction);
            transaction.setRule(rule);
            transaction.setDate(iteratorDateTime);

            transaction.setRule(rule);
            transactionService.addTransaction(transaction);
            rule.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.plusMonths(1);
        }
    }
}
