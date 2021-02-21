package space.deg.adam.controller.api.transaction;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.deg.adam.controller.api.transaction.request_bodies.AddTransactionRequestBody;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody AddTransactionRequestBody requestBody) {

    }
}
