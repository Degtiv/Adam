package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.operation.Operation;
import space.deg.adam.domain.operation.operation_rule.OperationRule;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.OperationRepository;
import space.deg.adam.service.OperationService;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static space.deg.adam.utils.RequestsUtils.getOperationPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

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
        fillModel(model, operations);
        return getOperationPage("operations");
    }

    @GetMapping
    public String operations(@AuthenticationPrincipal User user, Model model) {
        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, operations);
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
            Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

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

        System.out.println(operation);
        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, operations);
        return redirectPage("operations");
    }

    @PostMapping("/save/{operation}")
    public String transactionFormSave(@PathVariable Operation operation,
                                      @AuthenticationPrincipal User user,
                                      @RequestParam String title,
                                      @RequestParam String startDateText,
                                      @RequestParam String endDateText,
                                      @RequestParam BigDecimal amount,
                                      @RequestParam String description,
                                      @RequestParam String rule,
                                      @RequestParam String ruleParameter,
                                      @RequestParam String category,
                                      Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!operation.getUser().is(user)) return redirectPage("notPermited");

        operation.setTitle(title);
        operation.setStartDate(LocalDate.parse(startDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        operation.setEndDate(LocalDate.parse(endDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setRule(OperationRule.byTitle(rule));
        operation.setRuleParameter(ruleParameter);
        operation.setCategory(Category.byTitle(category));

        operationService.saveOperation(operation);

        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, operations);
        return redirectPage("operations");
    }

    @PostMapping("/delete/{operation}")
    public String transactionFormDelete(@PathVariable Operation operation,
                                        @AuthenticationPrincipal User user,
                                        Model model) {
        if (!operation.getUser().is(user)) return redirectPage("notPermited");

        operationService.deleteOperation(operation);

        Iterable<Operation> operations = operationRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, operations);
        return redirectPage("operations");
    }

    private void fillModel(Model model, Iterable<Operation> operations) {
        model.addAttribute("operations", operations);
        model.addAttribute("categories", Category.values());
        model.addAttribute("rules", OperationRule.values());
    }
}
