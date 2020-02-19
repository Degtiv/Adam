package space.deg.adam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;

import java.util.HashMap;

@RestController
public class ApiController {
    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping(path="status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> status(
            @AuthenticationPrincipal User user,
            @RequestBody String body) {
        System.out.println(body);

        Transaction transaction = transactionRepository.findByUser(user).iterator().next();

        HashMap<String, Object> result = new HashMap<>();
        result.put("a", transaction.getAmount());
        result.put("b", transaction.getCategory());
        return result;
    }
}
