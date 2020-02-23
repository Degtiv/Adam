package space.deg.adam.service;

import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.balance.DetailBalance;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.BalanceRepository;
import space.deg.adam.repository.GoalRepository;
import space.deg.adam.repository.TransactionRepository;

import java.time.LocalDateTime;

@Service
public class DetailBalanceService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    GoalRepository goalRepository;

    public DetailBalance getDetailBalance(User user, LocalDateTime start, LocalDateTime end){
        Iterable<Transaction> transactions = transactionRepository.findByUserAndDateAfterAndDateBefore(user, start, end, Sort.by(Sort.Direction.DESC, "date"));
        return new DetailBalance();
    }
}
