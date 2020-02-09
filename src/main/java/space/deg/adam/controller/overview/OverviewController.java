package space.deg.adam.controller.overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.deg.adam.domain.balance.Balance;
import space.deg.adam.domain.goals.Category;
import space.deg.adam.domain.goals.Status;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.BalanceRepository;
import space.deg.adam.repository.TransactionRepository;

import static space.deg.adam.utils.RequestsUtils.getOverviewPage;
import static space.deg.adam.utils.RequestsUtils.getTransactionPage;

@Controller
@RequestMapping("/overview")
public class OverviewController {
    @Autowired
    private BalanceRepository balanceRepository;

    @GetMapping
    public String goals(@AuthenticationPrincipal User user,
                        Model model) {
        Iterable<Balance> balances = balanceRepository.findByUser(user);
        model.addAttribute("balances", balances);
        return getOverviewPage("overview");
    }
}
