package space.deg.adam.controller.administrative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.UserRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static space.deg.adam.utils.RequestsUtils.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return getAdminPage("user");
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return getAdminPage("userEditForm");
    }

    @PostMapping("/delete/{deletedUser}")
    public String deleteUser(@PathVariable User deletedUser, @AuthenticationPrincipal User loginedUser) {
        if (!loginedUser.isAdmin()) return redirectPage(getErrorPage("notPermited"));
        userRepository.delete(deletedUser);
        return redirectPage("user");
    }

    @PostMapping
    public String saveUser(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);

        final Set<String> roles= Arrays.stream(Role.values())
                .map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for(String role : form.keySet()) {
            if (roles.contains(role))
                user.getRoles().add(Role.valueOf(role));
        }

        userRepository.save(user);

        return getAdminPage("user");
    }
}
