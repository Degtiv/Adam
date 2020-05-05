package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.deg.adam.domain.common.Category;
import space.deg.adam.domain.common.Status;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.user.User;
import space.deg.adam.domain.user.events.FirstEnterUserEvent;
import space.deg.adam.repository.GoalRepository;
import space.deg.adam.repository.UserRepository;

import java.math.BigDecimal;

@Controller
public class LandingController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GoalRepository goalRepository;

    @GetMapping("/landing")
    public String greeting(Model model) {
        return "landing";
    }

    @GetMapping("/")
    public String landing() {
        return "redirect:/landing";
    }

    @PostMapping("/firstEnter/finish")
    public String firstEnterFinish(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String dateText,
            @RequestParam BigDecimal amount
    ) {
        Goal goal = Goal.newBuilder()
                .user(user)
                .title(title)
                .date(dateText)
                .amount(amount)
                .currency("RUR")
                .status(Status.PLANNED.getTitle())
                .category(Category.BASE.getTitle())
                .build();


        user.getEvents()
                .stream()
                .filter(t -> t.getTitle().equalsIgnoreCase(FirstEnterUserEvent.getEventTitle()))
                .findFirst()
                .ifPresent(userEvent -> userEvent.isActive = false);

        userRepository.save(user);

        goalRepository.save(goal);
        return "redirect:/goals";
    }

    @PostMapping("/firstEnter/hide")
    public String firstEnterHide(@AuthenticationPrincipal User user) {
        user.getEvents()
                .stream()
                .filter(t -> t.getTitle().equalsIgnoreCase(FirstEnterUserEvent.getEventTitle()))
                .findFirst()
                .ifPresent(userEvent -> userEvent.isActive = false);

        userRepository.save(user);

        return "redirect:/goals";
    }
}