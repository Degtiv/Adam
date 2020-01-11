package space.deg.adam.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static space.deg.adam.utils.RequestsUtils.getTransactionPage;

@Controller
public class TransactionsController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public String debug (Model model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        return getTransactionPage("transactions");
    }

    //TODO: rewrite milestone logic, rewrite category logic
    @PostMapping("/transactions")
    public String add (
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String dateText,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam String status,
            @RequestParam String categoryName,
            Model model){

        Date date = null;
        try {
            date =  new SimpleDateFormat ("yyyy-MM-dd").parse(dateText);
        }catch (ParseException e) {
            System.out.println("Ошибка при получении даты");
        }

        Transaction transaction = new Transaction(user, amount, "RUR", date, title, description, status, categoryName);

        transactionRepository.save(transaction);
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        return getTransactionPage("transactions");
    }
}
