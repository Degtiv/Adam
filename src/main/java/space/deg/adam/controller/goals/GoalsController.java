package space.deg.adam.controller.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.goals.Category;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.goals.Status;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.GoalRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static space.deg.adam.utils.RequestsUtils.*;

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
            Model model) throws IOException, ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);

        Goal goal = new Goal(user, title, description, date, amount, "RUR", status, url, category);

        saveImage(image, goal);

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
                                   Model model) throws IOException, ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);

        goal.setTitle(title);
        goal.setDate(date);
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
                                  Model model) throws IOException, ParseException {
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
                                  Model model) throws IOException, ParseException {
        if (!goal.getUser().is(user)) return redirectPage("notPermited");
        saveImage(image, goal);

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



    private void saveImage(MultipartFile image, Goal goal) throws IOException {
        if (image != null && !image.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String imageName = UUID.randomUUID().toString() + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + "/" + imageName));
            goal.setImage(imageName);
        }
    }
}
