package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.deg.adam.domain.Transaction;
import space.deg.adam.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class DebugController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/debug")
    public String debug (Map<String, Object> model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.put("transactions", transactions);

        return "debug";
    }

    @PostMapping("/debug")
    public String add (
            @RequestParam BigDecimal sum,
            @RequestParam String category,
            @RequestParam String comment,
            Map<String, Object> model) {
        System.out.println(sum.toString() +" " + category + " " + comment);
        Transaction transaction = new Transaction(sum, category, comment);
        transactionRepository.save(transaction);
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.put("transactions", transactions);

        return "debug";
    }
}
