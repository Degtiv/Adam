package space.deg.adam.controller.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.transaction.filter.TransactionFilter;
import space.deg.adam.domain.transaction.filter.TransactionFilterType;
import space.deg.adam.domain.transaction.goals.Goal;
import space.deg.adam.domain.transaction.goals.GoalUtils;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.GoalRepository;
import space.deg.adam.service.GoalService;
import space.deg.adam.service.TransactionFilterService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import static space.deg.adam.constants.CommonConstants.MICRO_SECONDS_IN_DAY;
import static space.deg.adam.utils.RequestsUtils.getGoalPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

@Controller
@RequestMapping("/goals")
public class GoalsController {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private TransactionFilterService transactionFilterService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String goals(@AuthenticationPrincipal User user,
                        Model model) {
        fillModelForGoals(model,user);
        return getGoalPage("goals");
    }

    @PostMapping
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String dateText,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam String status,
            @RequestParam String url,
            @RequestParam String category,
            @RequestParam MultipartFile image,
            Model model) throws IOException {

        Goal goal = ((Goal.Builder) Goal.builder()
                .user(user)
                .title(title)
                .description(description)
                .date(dateText)
                .amount(amount)
                .currency("RUR")
                .status(Status.byTitle(status)))
                .url(url)
                .category(Category.byTitle(category))
                .build();

        GoalUtils.saveImage(uploadPath, image, goal);

        goalService.addGoal(goal);

        fillModelForGoals(model, user);
        return redirectPage("goals");
    }

    @GetMapping("/edit/{goal}")
    public String goalEditForm(@AuthenticationPrincipal User user, @PathVariable Goal goal, Model model) {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");

        fillModelForOneGoal(model, user, goal);
        return getGoalPage("goalEdit");
    }

    @PostMapping("/edit/{goal}")
    public String goalEditFormSave(@PathVariable Goal goal,
                                   @AuthenticationPrincipal User user,
                                   @RequestParam String title,
                                   @RequestParam String dateText,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam String description,
                                   @RequestParam String status,
                                   @RequestParam String url,
                                   @RequestParam String category,
                                   Model model) throws ParseException {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        goalService.deleteGoal(goal);
        goal.setTitle(title);
        goal.setDate(dateText);
        goal.setAmount(amount);
        goal.setCurrency("RUR");
        goal.setDescription(description);
        goal.setStatus(Status.byTitle(status));
        goal.setUrl(url);
        goal.setCategory(Category.byTitle(category));

        goalService.addGoal(goal);
        fillModelForOneGoal(model, user, goal);
        return redirectToEditGoalPage(goal);
    }

    @PostMapping("/edit/deleteImage/{goal}")
    public String goalDeleteImage(@PathVariable Goal goal,
                                  @AuthenticationPrincipal User user,
                                  Model model) {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        goal.setImage(null);

        goalRepository.save(goal);
        fillModelForOneGoal(model, user, goal);
        return redirectToEditGoalPage(goal);
    }

    @PostMapping("/edit/addImage/{goal}")
    public String goalAddImage(@PathVariable Goal goal,
                               @AuthenticationPrincipal User user,
                               @RequestParam MultipartFile image,
                               Model model) throws IOException {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        GoalUtils.saveImage(uploadPath, image, goal);

        goalRepository.save(goal);
        fillModelForOneGoal(model, user, goal);
        return redirectToEditGoalPage(goal);
    }

    @PostMapping("/delete/{goal}")
    public String goalDelete(@PathVariable Goal goal,
                             @AuthenticationPrincipal User user,
                             Model model) {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        goalService.deleteGoal(goal);

        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return redirectPage("goals");
    }

    @PostMapping("/filter")
    public String filterTransaction(
            @AuthenticationPrincipal User user,
            @RequestParam String fromDateText,
            @RequestParam String toDateText,
            Model model) {

        LocalDateTime fromDate =  LocalDate.parse(fromDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime toDate =  LocalDate.parse(toDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();

        transactionFilterService.setup(user, fromDate, toDate, TransactionFilterType.GOAL);

        fillModelForGoals(model, user);
        return redirectPage("goals");
    }

    @GetMapping("/clear_filter")
    public String clearFilter(
            @AuthenticationPrincipal User user,
            Model model) {

        transactionFilterService.clearFilter(user, TransactionFilterType.GOAL);

        fillModelForGoals(model, user);
        return redirectPage("goals");
    }

    public String redirectToEditGoalPage(Goal goal) {
        return redirectPage("goals/edit/" + goal.getUuid());
    }

    private void fillModelForOneGoal(Model model, User user, Goal goal) {
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
    }

    private void fillModelForGoals(Model model, User user) {
        TransactionFilter filter = transactionFilterService.getFilter(user, TransactionFilterType.GOAL);
        Iterable<Goal> goals = getGoalsByFilter(user, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("goals", goals);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
    }

    private Iterable<Goal> getGoalsByFilter(User user, TransactionFilter filter) {
        Iterable<Goal> goals;

        if (!filter.getIsActive())
            goals = goalRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
        else
            goals = goalRepository.findByUserAndDateAfterAndDateBefore(user,
                    filter.getFromDate().with(ChronoField.MICRO_OF_DAY, MICRO_SECONDS_IN_DAY - 1L).minusDays(1),
                    filter.getToDate().with(ChronoField.MICRO_OF_DAY, 0).plusDays(1),
                    Sort.by(Sort.Direction.DESC, "date"));

        return goals;
    }
}
