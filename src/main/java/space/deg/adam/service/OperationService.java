package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operation_rule.OperationRuleStrategy;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.repository.OperationRepository;
import space.deg.adam.repository.TransactionRepository;

import java.lang.reflect.InvocationTargetException;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    public void addOperation(Operation operation) {
        operationRepository.save(operation);

        try {
            OperationRuleStrategy strategy = operation.getRule().getStrategyClass();
            strategy.setTransactionService(transactionService);
            strategy.generateTransactions(operation);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        operationRepository.save(operation);
    }

    public void saveOperation(Operation operation) {
        operationRepository.save(operation);
    }

    public void deleteOperation(Operation operation) {
        Iterable<Transaction> transactions = transactionRepository.findByUserAndOperation(operation.getUser(), operation);
        transactions.forEach(transaction -> transactionService.deleteTransaction(transaction));
        operationRepository.delete(operation);
    }
}