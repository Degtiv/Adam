package space.deg.adam.controller.administrative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

import static space.deg.adam.utils.RequestsUtils.*;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

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
        userRepository.save(user);

        return getAdminPage("login");
    }
}
