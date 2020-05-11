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
    public void generateTransactions(Rule rule) {
        LocalDateTime iteratorDateTime = rule.getStartDate();
        LocalDateTime end = rule.getEndDate();
        int parameterDay = Integer.valueOf(rule.getRuleParameter());

        if (iteratorDateTime.getDayOfMonth() > parameterDay)
            iteratorDateTime = iteratorDateTime.plusMonths(1);

        iteratorDateTime = iteratorDateTime.withDayOfMonth(parameterDay);

        while (!iteratorDateTime.isAfter(end)) {
            if (iteratorDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    iteratorDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
                iteratorDateTime.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

            Transaction transaction = ((Transaction.Builder) Transaction.builder()
                    .user(rule.getUser())
                    .title(rule.getTitle())
                    .date(iteratorDateTime)
                    .amount(rule.getAmount())
                    .currency("RUR")
                    .transactionType(rule.getTransactionType())
                    .description(rule.getDescription())
                    .status(Status.PLANNED))
                    .category(rule.getCategory())
                    .build();

            transaction.setRule(rule);
            transactionService.addTransaction(transaction);
            rule.addTransaction(transaction);
            iteratorDateTime = iteratorDateTime.plusMonths(1);
        }
    }
}
