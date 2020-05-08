package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MilestoneService milestoneService;

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        milestoneService.addTransactionToMilestones(transaction.getUser(), transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        milestoneService.removeTransactionFromMilestones(transaction.getUser(), transaction);
        transactionRepository.delete(transaction);
    }
}
