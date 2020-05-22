package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.strategies.AbstractStrategy;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.repository.RuleRepository;
import space.deg.adam.repository.TransactionRepository;

import java.lang.reflect.InvocationTargetException;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    public void addRule(Rule rule, Transaction referenceTransaction) {
        ruleRepository.save(rule);

        try {
            AbstractStrategy strategy = rule.getRuleStrategy().getStrategyClass();
            strategy.setTransactionService(transactionService);
            strategy.generateTransactions(rule, referenceTransaction);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        ruleRepository.save(rule);
    }

    public void saveRule(Rule rule) {
        ruleRepository.save(rule);
    }

    public void deleteRule(Rule rule) {
        Iterable<Transaction> transactions = transactionRepository.findByUserAndRule(rule.getUser(), rule);
        transactions.forEach(transaction -> transactionService.deleteTransaction(transaction));
        ruleRepository.delete(rule);
    }
}
