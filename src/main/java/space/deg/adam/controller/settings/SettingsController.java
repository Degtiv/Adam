package space.deg.adam.controller.settings;

import static space.deg.adam.utils.RequestsUtils.getOverviewPage;
import static space.deg.adam.utils.RequestsUtils.getSettingsPage;
import static space.deg.adam.utils.RequestsUtils.redirectPage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.deg.adam.domain.OverviewFilter;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.MilestoneRepository;
import space.deg.adam.service.OverviewFilterService;
import space.deg.adam.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {

  @Autowired
  UserService userService;

  @GetMapping
  public String settigns(@AuthenticationPrincipal User user, Model model) {
    fillModel(model, user);
    return getSettingsPage("settings");
  }

  private void fillModel(Model model, User user) {
    String tgKey = userService.generateAccessToken(user.getUsername());
    model.addAttribute("tgKey", tgKey);
  }
}
