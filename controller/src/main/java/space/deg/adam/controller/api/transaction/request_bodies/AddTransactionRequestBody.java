package space.deg.adam.controller.api.transaction.request_bodies;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class AddTransactionRequestBody {
    String username;
    List<TransactionGen> transactions;

    @NoArgsConstructor
    @Data
    public static class TransactionGen {
        String title;

        @Nullable
        String description;
        String date;
        BigDecimal amount;
        String currency;
        String status;
        String accountTitle;
    }
}
