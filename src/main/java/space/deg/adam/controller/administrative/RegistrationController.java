package space.deg.adam.controller.administrative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.deg.adam.domain.balance.Balance;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.domain.user.events.FirstEnterUserEvent;
import space.deg.adam.repository.BalanceRepository;
import space.deg.adam.repository.UserRepository;
import space.deg.adam.utils.FirstSecondOfMonth;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;

import static space.deg.adam.utils.RequestsUtils.getAdminPage;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BalanceRepository balanceRepository;

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
        LocalDateTime now = LocalDateTime.now().with(FirstSecondOfMonth.adjust());
        LocalDateTime firstMonth = now.minusMonths(12);
        LocalDateTime lastMonth = now.plusMonths(24);
        long monthsBetween = ChronoUnit.MONTHS.between(firstMonth, lastMonth);
        for (long month = 0; month < monthsBetween; month++) {
            LocalDateTime dateTime = firstMonth.plusMonths(month);
            Balance balance = Balance.newBuilder().user(user).date(dateTime).build();
            balanceRepository.save(balance);
        }
    }
}
