package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import space.deg.adam.domain.Transaction;
import space.deg.adam.repository.TransactionRepository;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/main")
    public String main (Map<String, Object> model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.put("transactions", transactions);

        return "main";
    }
}
