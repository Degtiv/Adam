package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.service.BalanceService;
import space.deg.adam.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static space.deg.adam.utils.RequestsUtils.getTransactionPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public String transactions(
            @AuthenticationPrincipal User user,
            Model model) {
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));

        fillModel(model, transactions);
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

        Transaction transaction = Transaction.builder()
                .user(user)
                .title(title)
                .date(LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                .amount(amount)
                .description(description)
                .status(Status.byTitle(status))
                .category(Category.byTitle(category))
                .currency("RUR")
                .build();

        transactionService.addTransaction(transaction);


        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
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

        transactionService.deleteTransaction(transaction);

        transaction.setTitle(title);
        transaction.setDate(LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setStatus(Status.byTitle(status));
        transaction.setCategory(Category.byTitle(category));

        transactionRepository.save(transaction);
        transactionService.addTransaction(transaction);

        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return getTransactionPage("transactions");
    }

    @PostMapping("/delete/{transaction}")
    public String transactionFormDelete(@PathVariable Transaction transaction,
                                        @AuthenticationPrincipal User user,
                                        Model model) {
        if (!transaction.getUser().is(user)) return redirectPage("notPermited");

        transactionService.deleteTransaction(transaction);

        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return getTransactionPage("transactions");
    }

    private void fillModel(Model model, Iterable<Transaction> transactions) {
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
    }

    @GetMapping("/deleteAll")
    public String deleteAll(@AuthenticationPrincipal User user,
                                        Model model) {
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        transactions.forEach(transaction -> transactionService.deleteTransaction(transaction));

        transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return getTransactionPage("transactions");
    }
}
