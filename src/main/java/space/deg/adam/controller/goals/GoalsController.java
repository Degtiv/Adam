package space.deg.adam.controller.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.goals.GoalUtils;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.GoalRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

import static space.deg.adam.utils.RequestsUtils.getGoalPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

@Controller
@RequestMapping("/goals")
public class GoalsController {
    @Autowired
    private GoalRepository goalRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String goals(@AuthenticationPrincipal User user,
                        Model model) {
        Iterable<Goal> goals = goalRepository.findByUser(user);
        model.addAttribute("goals", goals);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
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

        Goal goal = Goal.newBuilder()
                .user(user)
                .title(title)
                .description(description)
                .date(dateText)
                .amount(amount)
                .currency("RUR")
                .status(status)
                .url(url)
                .category(category)
                .build();

        GoalUtils.saveImage(uploadPath, image, goal);

        goalRepository.save(goal);
        Iterable<Goal> goals = goalRepository.findByUser(user);
        model.addAttribute("goals", goals);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getGoalPage("goals");
    }

    @GetMapping("/edit/{goal}")
    public String goalEditForm(@AuthenticationPrincipal User user, @PathVariable Goal goal, Model model) {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
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

        goal.setTitle(title);
        goal.setDate(dateText);
        goal.setAmount(amount);
        goal.setDescription(description);
        goal.setStatus(status);
        goal.setUrl(url);
        goal.setCategory(category);

        goalRepository.save(goal);
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getGoalPage("goalEdit");
    }

    @PostMapping("/deleteImage/{goal}")
    public String goalDeleteImage(@PathVariable Goal goal,
                                  @AuthenticationPrincipal User user,
                                  Model model) {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        goal.setImage(null);

        goalRepository.save(goal);
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getGoalPage("goalEdit");
    }

    @PostMapping("/addImage/{goal}")
    public String goalAddImage(@PathVariable Goal goal,
                               @AuthenticationPrincipal User user,
                               @RequestParam MultipartFile image,
                               Model model) throws IOException {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        GoalUtils.saveImage(uploadPath, image, goal);

        goalRepository.save(goal);
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return getGoalPage("goalEdit");
    }

    @PostMapping("/delete/{goal}")
    public String goalDelete(@PathVariable Goal goal,
                             @AuthenticationPrincipal User user,
                             Model model) {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        goalRepository.delete(goal);

        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return redirectPage("goals");
    }
}
