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
import space.deg.adam.service.DetailBalanceService;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
public class ApiController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DetailBalanceService detailBalanceService;

    @PostMapping(path="status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> status(
            @AuthenticationPrincipal User user,
            @RequestBody String body) {
        Transaction transaction = transactionRepository.findByUser(user).iterator().next();
        System.out.println("enter apicontroller");
        System.out.println(detailBalanceService.getDetailBalance(user, LocalDateTime.now().minusWeeks(1), LocalDateTime.now().plusWeeks(1)));

        HashMap<String, Object> result = new HashMap<>();
        result.put("a", transaction.getAmount());
        result.put("b", transaction.getCategory());
        return result;
    }
}
