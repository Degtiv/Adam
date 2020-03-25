package space.deg.adam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import space.deg.adam.domain.balance.DetailBalance;
import space.deg.adam.domain.transaction.Transaction;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.service.DetailBalanceService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class ApiController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DetailBalanceService detailBalanceService;

    @PostMapping(path="status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DetailBalance status(
            @AuthenticationPrincipal User user,
            @RequestBody OverviewDates dates) throws JsonProcessingException {
        Transaction transaction = transactionRepository.findByUser(user).iterator().next();
        return detailBalanceService.getDetailBalance(user, dates.getStart(), dates.getEnd());
    }

    @NoArgsConstructor
    public static class OverviewDates {
        String start;
        String end;

        public LocalDateTime getStart() {
            return LocalDate.parse(start).atStartOfDay();
        }

        public LocalDateTime getEnd() {
            return LocalDate.parse(end).atStartOfDay();
        }

        @Override
        public String toString() {
            return "start: " + start + "\n" + "end: " + end;
        }
    }
}
