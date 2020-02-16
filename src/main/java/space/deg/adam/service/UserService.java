package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.BalanceRepository;
import space.deg.adam.repository.GoalRepository;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private UserRepository userRepository;

    public void deleteUser(User user) {
        transactionRepository.findByUser(user).forEach(transaction -> transactionRepository.delete(transaction));
        goalRepository.findByUser(user).forEach(goal -> goalRepository.delete(goal));
        balanceRepository.findByUser(user).forEach(balance -> balanceRepository.delete(balance));

        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
