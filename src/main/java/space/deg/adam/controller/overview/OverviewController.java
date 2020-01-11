package space.deg.adam.controller.overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.deg.adam.domain.goals.Category;
import space.deg.adam.domain.goals.Goal;
import space.deg.adam.domain.goals.Status;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.GoalRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static space.deg.adam.utils.RequestsUtils.*;

@Controller
@RequestMapping("/overview")
public class OverviewController {
    @GetMapping
    public String goals(@AuthenticationPrincipal User user,
                        Model model) {
        return getOverviewPage("overview");
    }
}
