package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.deg.adam.domain.Transaction;
import space.deg.adam.domain.User;
import space.deg.adam.repository.CategoryRepository;
import space.deg.adam.repository.TransactionRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DebugController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/debug")
    public String debug (Model model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        return "debug";
    }

    //TODO: rewrite milestone logic, rewrite category logic
    @PostMapping("/debug")
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

        return "debug";
    }
}
