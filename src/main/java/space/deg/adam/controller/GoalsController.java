package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.Category;
import space.deg.adam.domain.Goal;
import space.deg.adam.domain.Status;
import space.deg.adam.domain.User;
import space.deg.adam.repository.GoalRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
        return "goals";
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
        return "goals";
    }

    @GetMapping("/edit/{goal}")
    public String goalEditForm(@AuthenticationPrincipal User user, @PathVariable Goal goal, Model model) {
        if (!goal.getUser().is(user)) return "notPermited";
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return "goalEdit";
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
        return "goalEdit";
    }

    @PostMapping("/deleteImage/{goal}")
    public String goalDeleteImage(@PathVariable Goal goal,
                                  @AuthenticationPrincipal User user,
                                  Model model) throws IOException, ParseException {
        if (!goal.getUser().is(user)) return "notPermited";
        goal.setImage(null);

        goalRepository.save(goal);
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return "goalEdit";
    }

    @PostMapping("/addImage/{goal}")
    public String goalAddImage(@PathVariable Goal goal,
                               @AuthenticationPrincipal User user,
                               @RequestParam MultipartFile image,
                                  Model model) throws IOException, ParseException {
        if (!goal.getUser().is(user)) return "notPermited";
        saveImage(image, goal);

        goalRepository.save(goal);
        model.addAttribute("goal", goal);
        model.addAttribute("categories", Category.values());
        model.addAttribute("statuses", Status.values());
        return "goalEdit";
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
