package space.deg.adam.controller.overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.MilestoneRepository;

import static space.deg.adam.utils.RequestsUtils.getOverviewPage;

@Controller
@RequestMapping("/overview")
public class OverviewController {
    @Autowired
    private MilestoneRepository milestoneRepository;

    @GetMapping
    public String goals(@AuthenticationPrincipal User user) {
        return getOverviewPage("overview");
    }
}
