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
    private BalanceService balanceService;

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        balanceService.updateBalance(transaction.getUser(), transaction.getDate(),transaction.getAmount());
    }

    public void deleteTransaction(Transaction transaction) {
        balanceService.updateBalance(transaction.getUser(), transaction.getDate(), transaction.getAmount().negate());
        transactionRepository.delete(transaction);
    }
}
