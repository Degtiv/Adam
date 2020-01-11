package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.repository.TransactionRepository;

@Controller
public class MainController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/main")
    public String main (Model model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);

        return "main";
    }
}
