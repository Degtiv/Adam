package space.deg.adam.controller.administrative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.UserRepository;

import java.util.Collections;

import static space.deg.adam.utils.RequestsUtils.getAdminPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/create_admin")
    public String AdminCreation() {
        User user = userRepository.findByUsername("Admin");

        if (user == null) {
            user = new User();
            user.setUsername("Admin");
            user.setPassword("Admin");
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(user);
        }

        return redirectPage(getAdminPage("login"));
    }
}
