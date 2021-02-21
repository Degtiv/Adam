package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.account.Account;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account addAccount(User user, String title, String description, String currency) {
        Account account = new Account(user, title, description, currency);
        accountRepository.save(account);
        return account;
    }

    public Account getAccountByTitle(String title) {
        return accountRepository.findByTitle(title);
    }

}
