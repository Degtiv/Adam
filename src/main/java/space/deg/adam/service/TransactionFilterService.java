package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.transaction.filter.TransactionFilter;
import space.deg.adam.domain.transaction.filter.TransactionFilterType;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionFilterRepository;

import java.time.LocalDateTime;

@Service
public class TransactionFilterService {
    @Autowired
    TransactionFilterRepository transactionFilterRepository;

    public TransactionFilter getFilter(User user, TransactionFilterType transactionFilterType) {
        TransactionFilter transactionFilter = transactionFilterRepository.findByUserAndType(user, transactionFilterType);
        if (transactionFilter == null) {
            transactionFilter = new TransactionFilter();
            transactionFilter.setUser(user);
            transactionFilter.setType(transactionFilterType);
            transactionFilterRepository.save(transactionFilter);
        }

        return transactionFilterRepository.findByUserAndType(user, transactionFilterType);
    }

    public void setup(User user, LocalDateTime fromDate, LocalDateTime toDate, TransactionFilterType transactionFilterType) {
        TransactionFilter transactionFilter = getFilter(user, transactionFilterType);
        transactionFilter.setup(fromDate, toDate);
        transactionFilterRepository.save(transactionFilter);
    }

    public void clearFilter(User user, TransactionFilterType transactionFilterType) {
        TransactionFilter transactionFilter = getFilter(user, transactionFilterType);
        transactionFilter.clear();
        transactionFilterRepository.save(transactionFilter);
    }

    public boolean isActive(User user, TransactionFilterType transactionFilterType) {
        return getFilter(user, transactionFilterType).getIsActive();
    }
}
