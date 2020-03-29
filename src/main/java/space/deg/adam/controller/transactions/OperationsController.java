package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.OperationRule;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.OperationRepository;
import space.deg.adam.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static space.deg.adam.utils.RequestsUtils.getOperationPage;
import static space.deg.adam.utils.RequestsUtils.getTransactionPage;

@Controller
@RequestMapping("/operations")
public class OperationsController {
    @Autowired
    OperationRepository operationRepository;

    @Autowired
    OperationService operationService;

    @GetMapping("/deleteAll")
    public String deleteAll(@AuthenticationPrincipal User user, Model model) {
        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        operations.forEach(x -> operationRepository.delete(x));
        model.addAttribute("operations", operations);
        model.addAttribute("categories", Category.values());
        model.addAttribute("rules", OperationRule.values());
        return getOperationPage("operations");
    }

    @GetMapping
    public String operations(@AuthenticationPrincipal User user, Model model) {
        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        operations.forEach(x -> System.out.println(x.toString()));

        model.addAttribute("operations", operations);
        model.addAttribute("categories", Category.values());
        model.addAttribute("rules", OperationRule.values());
        return getOperationPage("operations");
    }

    @PostMapping
    public String addOperation(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String startDateText,
            @RequestParam String endDateText,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam String rule,
            @RequestParam String ruleParameter,
            @RequestParam String category,
            Model model) {
        System.out.println(title + "\n" +
                startDateText + "\n" +
                endDateText + "\n" +
                amount + "\n" +
                description + "\n" +
                rule + "\n" +
                ruleParameter + "\n" +
                category + "\n"
                );

        Operation operation = Operation.builder()
                .user(user)
                .title(title)
                .startDate(LocalDate.parse(startDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                .endDate(LocalDate.parse(endDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                .amount(amount)
                .description(description)
                .rule(OperationRule.byTitle(rule))
                .ruleParameter(ruleParameter)
                .category(Category.byTitle(category))
                .currency("RUR")
                .build();

        operationService.addOperation(operation);

        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        model.addAttribute("operations", operations);
        model.addAttribute("categories", Category.values());
        model.addAttribute("rules", OperationRule.values());
        return getOperationPage("operations");
    }
}
