package space.deg.adam.domain.rule.rule_strategy.strategies;

import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EveryYearStrategy extends AbstractStrategy{
    public EveryYearStrategy() {
        super.ruleStrategy = RuleStrategy.EVERY_YEAR;
    }

    @Override
    public void generateTransactions(Rule rule) {
        LocalDateTime iteratorDateTime = rule.getStartDate();
        LocalDateTime end = rule.getEndDate();
        LocalDateTime parameterDay = LocalDateTime.parse(rule.getRuleParameter(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        if (iteratorDateTime.isAfter(parameterDay))
            iteratorDateTime = iteratorDateTime.plusYears(1);

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
            iteratorDateTime = iteratorDateTime.plusYears(1);
        }
    }
}
