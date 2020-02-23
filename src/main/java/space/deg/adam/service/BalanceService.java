package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.BalanceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    public void updateBalance(User user, LocalDateTime startDate, BigDecimal amount) {
        balanceRepository.findByUserAndDateAfter(user, startDate).forEach(balance -> {
            balance.increaseAmount(amount);
            balanceRepository.save(balance);
        });
    }
}
