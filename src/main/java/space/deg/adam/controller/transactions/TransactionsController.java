package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.transaction.filter.TransactionFilter;
import space.deg.adam.domain.transaction.TransactionType;
import space.deg.adam.domain.transaction.filter.TransactionFilterType;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.service.MilestoneService;
import space.deg.adam.service.RuleService;
import space.deg.adam.service.TransactionFilterService;
import space.deg.adam.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import static space.deg.adam.constants.CommonConstants.MICRO_SECONDS_IN_DAY;
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
    TransactionRepository transactionRepository;

    @Autowired
    TransactionFilterService transactionFilterService;

    @GetMapping
    public String transactions(
            @AuthenticationPrincipal User user,
            Model model) {
        fillModel(model, user);
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

        fillModel(model, user);
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

        fillModel(model, user);
        return redirectPage("transactions");
    }

    @PostMapping("/delete/{transaction}")
    public String transactionFormDelete(@PathVariable Transaction transaction,
                                        @AuthenticationPrincipal User user,
                                        Model model) {
        if (!transaction.getUser().is(user)) return redirectPage("notPermited");

        transactionService.deleteTransaction(transaction);

        fillModel(model, user);
        return redirectPage("transactions");
    }

    @GetMapping("/deleteAll")
    public String deleteAll(@AuthenticationPrincipal User user,
                                        Model model) {
        Iterable<Transaction> transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        transactions.forEach(transaction -> transactionService.deleteTransaction(transaction));

        fillModel(model, user);
        return redirectPage("transactions");
    }

    @PostMapping("/filter")
    public String filterTransaction(
            @AuthenticationPrincipal User user,
            @RequestParam String fromDateText,
            @RequestParam String toDateText,
            Model model) {

        LocalDateTime fromDate =  LocalDate.parse(fromDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime toDate =  LocalDate.parse(toDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();

        transactionFilterService.setup(user, fromDate, toDate, TransactionFilterType.TRANSACTION);

        fillModel(model, user);
        return redirectPage("transactions");
    }

    @GetMapping("/clear_filter")
    public String clearFilter(
            @AuthenticationPrincipal User user,
            Model model) {

        transactionFilterService.clearFilter(user, TransactionFilterType.TRANSACTION);

        fillModel(model, user);
        return redirectPage("transactions");
    }

    private void fillModel(Model model, User user) {
        TransactionFilter filter = transactionFilterService.getFilter(user, TransactionFilterType.TRANSACTION);
        Iterable<Transaction> transactions = getTransactionByFilter(user, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("ruleStrategies", RuleStrategy.values());
    }

    private Iterable<Transaction> getTransactionByFilter(User user, TransactionFilter filter) {
        Iterable<Transaction> transactions;

        if (!filter.getIsActive())
            transactions = transactionRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        else
            transactions = transactionRepository.findByUserAndDateAfterAndDateBefore(user,
                    filter.getFromDate().with(ChronoField.MICRO_OF_DAY, MICRO_SECONDS_IN_DAY - 1L).minusDays(1),
                    filter.getToDate().with(ChronoField.MICRO_OF_DAY, 0).plusDays(1),
                    Sort.by(Sort.Direction.DESC, "date"));

        return transactions;
    }
}
