package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.Goal;
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
public class GoalsController {
    @Autowired
    private GoalRepository goalRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/goals")
    public String debug (Model model) {
        Iterable<Goal> goals = goalRepository.findAll();
        model.addAttribute("goals", goals);

        return "goals";
    }

    //TODO: rewrite milestone logic, rewrite category logic
    @PostMapping("/goals")
    public String add (
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



        Date date = null;
        try {
            date =  new SimpleDateFormat ("yyyy-MM-dd").parse(dateText);
        }catch (ParseException e) {
            System.out.println("Ошибка при получении даты");
        }

        Goal goal = new Goal(user, title, description, date, amount, "RUR", status, url, category);

        if (image != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String imageName = UUID.randomUUID().toString() + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + "/" + imageName));
            goal.setImage(imageName);
        }

        goalRepository.save(goal);
        Iterable<Goal> goals = goalRepository.findAll();
        model.addAttribute("goals", goals);

        return "goals";
    }
}
