package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.transaction.TransactionFilter;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionFilterRepository;

import java.time.LocalDateTime;

@Service
public class TransactionFilterService {
    @Autowired
    TransactionFilterRepository transactionFilterRepository;

    public TransactionFilter getFilter(User user) {
        TransactionFilter transactionFilter = transactionFilterRepository.findByUser(user);
        if (transactionFilter == null) {
            transactionFilter = new TransactionFilter();
            transactionFilter.setUser(user);
            transactionFilterRepository.save(transactionFilter);
        }

        return transactionFilterRepository.findByUser(user);
    }

    public void setup(User user, LocalDateTime fromDate, LocalDateTime toDate) {
        TransactionFilter transactionFilter = getFilter(user);
        transactionFilter.setup(fromDate, toDate);
        transactionFilterRepository.save(transactionFilter);
    }

    public void clearFilter(User user) {
        TransactionFilter transactionFilter = getFilter(user);
        transactionFilter.clear();
        transactionFilterRepository.save(transactionFilter);
    }

    public boolean isClear(User user) {
        return getFilter(user).getClear();
    }
}
