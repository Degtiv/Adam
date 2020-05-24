package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.transaction.TransactionType;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.service.MilestoneService;
import space.deg.adam.service.RuleService;
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
    RuleService ruleService;

    @Autowired
    MilestoneService milestoneService;

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
            @RequestParam String transactionType,
            @RequestParam String description,
            @RequestParam String status,
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "false") boolean isRepeated,
            @RequestParam(required = false) String startDateText,
            @RequestParam(required = false) String endDateText,
            @RequestParam(required = false) String ruleStrategy,
            @RequestParam(required = false) String ruleParameter,
            Model model) {

        Transaction transaction = ((Transaction.Builder) Transaction.builder()
                .user(user)
                .title(title)
                .date(LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                .amount(amount)
                .currency("RUR")
                .transactionType(TransactionType.byTitle(transactionType))
                .description(description)
                .status(Status.byTitle(status)))
                .category(Category.byTitle(category))
                .build();

        if (isRepeated) {
            Rule rule = Rule.builder()
                    .user(user)
                    .startDate(LocalDate.parse(startDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                    .endDate(LocalDate.parse(endDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                    .ruleStrategy(RuleStrategy.byTitle(ruleStrategy))
                    .ruleParameter(ruleParameter)
                    .build();

            ruleService.addRule(rule, transaction);
        } else {
            transactionService.addTransaction(transaction);
        }

        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return redirectPage("transactions");
    }

    @PostMapping("/save/{transaction}")
    public String transactionFormSave(@PathVariable Transaction transaction,
                                      @AuthenticationPrincipal User user,
                                      @RequestParam String title,
                                      @RequestParam String dateText,
                                      @RequestParam BigDecimal amount,
                                      @RequestParam String transactionType,
                                      @RequestParam String description,
                                      @RequestParam String status,
                                      @RequestParam String category,
                                      Model model) {
        if (!transaction.getUser().is(user)) return redirectPage("notPermited");

        transactionService.deleteTransaction(transaction);

        transaction.setTransactionType(TransactionType.byTitle(transactionType));
        transaction.setTitle(title);
        transaction.setDate(LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setStatus(Status.byTitle(status));
        transaction.setCategory(Category.byTitle(category));

        transactionService.addTransaction(transaction);

        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return redirectPage("transactions");
    }

    @PostMapping("/delete/{transaction}")
    public String transactionFormDelete(@PathVariable Transaction transaction,
                                        @AuthenticationPrincipal User user,
                                        Model model) {
        if (!transaction.getUser().is(user)) return redirectPage("notPermited");

        transactionService.deleteTransaction(transaction);

        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return redirectPage("transactions");
    }

    private void fillModel(Model model, Iterable<Transaction> transactions) {
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("ruleStrategies", RuleStrategy.values());
    }

    @GetMapping("/deleteAll")
    public String deleteAll(@AuthenticationPrincipal User user,
                                        Model model) {
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        transactions.forEach(transaction -> transactionService.deleteTransaction(transaction));

        transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        fillModel(model, transactions);
        return redirectPage("transactions");
    }
}
