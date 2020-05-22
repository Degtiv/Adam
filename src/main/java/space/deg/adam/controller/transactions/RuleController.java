package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.rule.Rule;
import space.deg.adam.domain.rule.rule_strategy.RuleStrategy;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.RuleRepository;
import space.deg.adam.service.RuleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static space.deg.adam.utils.RequestsUtils.getRulePage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

@Controller
@RequestMapping("/rules")
public class RuleController {
    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    RuleService ruleService;

    @GetMapping("/deleteAll")
    public String deleteAll(@AuthenticationPrincipal User user, Model model) {
        Iterable<Rule> rules = ruleRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        rules.forEach(x -> ruleRepository.delete(x));
        fillModel(model, rules);
        return getRulePage("rules");
    }

    @GetMapping
    public String rules(@AuthenticationPrincipal User user, Model model) {
        Iterable<Rule> rules = ruleRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, rules);
        return getRulePage("rules");
    }

    @PostMapping
    public String addRule(
            @AuthenticationPrincipal User user,
            Model model) {

        Iterable<Rule> rules = ruleRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, rules);
        return redirectPage("rules");
    }

    @PostMapping("/save/{rule}")
    public String transactionFormSave(@PathVariable Rule rule,
                                      @AuthenticationPrincipal User user,
                                      @RequestParam String startDateText,
                                      @RequestParam String endDateText,
                                      @RequestParam String ruleStrategy,
                                      @RequestParam String ruleParameter,
                                      Model model) {
        if (!rule.getUser().is(user)) return redirectPage("notPermited");

        rule.setStartDate(LocalDate.parse(startDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        rule.setEndDate(LocalDate.parse(endDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
        rule.setRuleStrategy(RuleStrategy.byTitle(ruleStrategy));
        rule.setRuleParameter(ruleParameter);

        ruleService.saveRule(rule);

        Iterable<Rule> rules = ruleRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, rules);
        return redirectPage("rules");
    }

    @PostMapping("/delete/{rule}")
    public String transactionFormDelete(@PathVariable Rule rule,
                                        @AuthenticationPrincipal User user,
                                        Model model) {
        if (!rule.getUser().is(user)) return redirectPage("notPermited");

        ruleService.deleteRule(rule);

        Iterable<Rule> rules = ruleRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "startDate"));
        fillModel(model, rules);
        return redirectPage("rules");
    }

    private void fillModel(Model model, Iterable<Rule> rules) {
        model.addAttribute("rules", rules);
        model.addAttribute("categories", Category.values());
        model.addAttribute("ruleStrategies", RuleStrategy.values());
    }
}
