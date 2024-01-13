package space.deg.adam.controller.overview;

import static space.deg.adam.utils.RequestsUtils.getOverviewPage;
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

@Controller
@RequestMapping("/overview")
public class OverviewController {
  @Autowired
  private MilestoneRepository milestoneRepository;

  @Autowired
  private OverviewFilterService overviewFilterService;

  @GetMapping
  public String overview(@AuthenticationPrincipal User user, Model model) {
    fillModel(model, user);
    return getOverviewPage("overview");
  }

  @GetMapping("/clear_filter")
  public String clearFilter(
      @AuthenticationPrincipal User user,
      Model model) {

    overviewFilterService.clearFilter(user);

    fillModel(model, user);
    return redirectPage("overview");
  }

  @PostMapping("/filter")
  public String filterTransaction(
      @AuthenticationPrincipal User user,
      @RequestParam String fromDateText,
      @RequestParam String toDateText,
      @RequestParam(defaultValue = "false") Boolean showDots,
      Model model) {

    LocalDateTime fromDate = LocalDate.parse(fromDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    LocalDateTime toDate = LocalDate.parse(toDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();

    overviewFilterService.setup(user, fromDate, toDate, showDots);

    fillModel(model, user);
    return redirectPage("overview");
  }

  private void fillModel(Model model, User user) {
    OverviewFilter filter = overviewFilterService.getFilter(user);
    model.addAttribute("overviewFilter", filter);
  }
}
