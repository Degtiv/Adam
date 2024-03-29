package space.deg.adam.controller.administrative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.deg.adam.domain.balance.Milestone;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.domain.user.events.FirstEnterUserEvent;
import space.deg.adam.repository.MilestoneRepository;
import space.deg.adam.repository.UserRepository;
import space.deg.adam.utils.FirstSecondOfMonth;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;

import static space.deg.adam.utils.RequestsUtils.getAdminPage;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        model.put("message", "");
        return getAdminPage("registration");
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromRepository = userRepository.findByUsername(user.getUsername());

        if (userFromRepository != null) {
            model.addAttribute("message", "User exist!");
            return getAdminPage("registration");
        } else {
            model.addAttribute("message", "");
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        FirstEnterUserEvent firstEnterUserEvent = new FirstEnterUserEvent();
        firstEnterUserEvent.isActive = true;
        user.addUserEvent(new FirstEnterUserEvent());
        userRepository.save(user);

        generateBalanceList(user);
        return getAdminPage("login");
    }

    private void generateBalanceList(User user) {
        LocalDateTime firstDayOfMonth = LocalDateTime.now().with(ChronoField.DAY_OF_MONTH, 1).with(ChronoField.MICRO_OF_DAY, 0);
        LocalDateTime firstMonth = firstDayOfMonth.minusMonths(12);
        LocalDateTime lastMonth = firstDayOfMonth.plusMonths(24);
        long monthsBetween = ChronoUnit.MONTHS.between(firstMonth, lastMonth);
        for (long month = 0; month < monthsBetween; month++) {
            LocalDateTime dateTime = firstMonth.plusMonths(month);
            Milestone milestone = Milestone.newBuilder().user(user).date(dateTime).build();
            milestoneRepository.save(milestone);
        }
    }
}
