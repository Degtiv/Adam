package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;

public class EveryDayStrategy extends AbstractStrategy {
    public EveryDayStrategy() {
        super.ruleStrategy = RuleStrategy.EVERY_DAY;
    }

    @Override
    public void generateTransactions(Rule rule) {
        LocalDateTime iteratorDateTime = rule.getStartDate();
        LocalDateTime end = rule.getEndDate();

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
            iteratorDateTime = iteratorDateTime.plusDays(1);
        }
    }
}
