package space.deg.adam.controller.overview;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.deg.adam.domain.user.User;

import static space.deg.adam.utils.RequestsUtils.getOverviewPage;

@Controller
@RequestMapping("/overview")
public class OverviewController {
    @GetMapping
    public String goals(@AuthenticationPrincipal User user,
                        Model model) {
        return getOverviewPage("overview");
    }
}
