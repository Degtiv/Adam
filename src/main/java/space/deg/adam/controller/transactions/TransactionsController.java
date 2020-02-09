package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.goals.Category;
import space.deg.adam.domain.goals.Status;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;

import java.math.BigDecimal;

import static space.deg.adam.utils.RequestsUtils.getTransactionPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public String transactions(
            @AuthenticationPrincipal User user,
            Model model) {
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getTransactionPage("transactions");
    }

    @PostMapping
    public String addTransaction(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String dateText,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam String status,
            @RequestParam String category,
            Model model) {

        Transaction transaction = Transaction.newBuilder()
                .user(user)
                .title(title)
                .date(dateText)
                .amount(amount)
                .description(description)
                .status(status)
                .category(category)
                .currency("RUR")
                .build();

        transactionRepository.save(transaction);
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getTransactionPage("transactions");
    }

    @PostMapping("/save/{transaction}")
    public String transactionFormSave(@PathVariable Transaction transaction,
                                      @AuthenticationPrincipal User user,
                                      @RequestParam String title,
                                      @RequestParam String dateText,
                                      @RequestParam BigDecimal amount,
                                      @RequestParam String description,
                                      @RequestParam String status,
                                      @RequestParam String category,
                                      Model model) {
        if (!transaction.getUser().is(user)) return redirectPage("notPermited");

        transaction.setTitle(title);
        transaction.setDate(dateText);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setStatus(status);
        transaction.setCategory(category);

        transactionRepository.save(transaction);
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getTransactionPage("transactions");
    }

    @PostMapping("/delete/{transaction}")
    public String transactionFormDelete(@PathVariable Transaction transaction,
                                        @AuthenticationPrincipal User user,
                                        Model model) {
        if (!transaction.getUser().is(user)) return redirectPage("notPermited");

        transactionRepository.delete(transaction);
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getTransactionPage("transactions");
    }
}
