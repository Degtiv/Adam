package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;

public class EveryMonthStrategy extends AbstractStrategy {
    public EveryMonthStrategy() {
        super.ruleStrategy = RuleStrategy.EVERY_MONTH;
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
