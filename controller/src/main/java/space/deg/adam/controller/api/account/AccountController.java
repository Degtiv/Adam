package space.deg.adam.controller.api.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.controller.api.account.request_bodies.AddAccountRequestBody;
import space.deg.adam.domain.account.Account;
import space.deg.adam.domain.token.Token;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.service.AccountService;
import space.deg.adam.service.TokenService;
import space.deg.adam.service.UserService;
import space.deg.adam.controller.api.utils.Error;

import java.util.Collections;

@RestController
@RequestMapping("api/account")
public class AccountController {
    public static final Role REQUIRED_ADD_ACCOUNT_ROLE = Role.USER;
    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    TokenService tokenService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestHeader("token") String tokenHash, @RequestBody AddAccountRequestBody requestBody) {
        if (!tokenService.checkTokenHashForUserAndRole(tokenHash, requestBody.getUsername(), REQUIRED_ADD_ACCOUNT_ROLE)) {
            Error error = Error.builder()
                    .message("Token not valid")
                    .solution("Please get new token")
                    .build();
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        if (accountService.getAccountByTitle(requestBody.getTitle()) != null) {
            Error error = new Error();
            error.setMessage("Account with title " + requestBody.getTitle() + " already exists.");
            error.setSolution("Please choose another title");
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        User user = (User) userService.loadUserByUsername(requestBody.getUsername());

        Account account = accountService.addAccount(user,
                requestBody.getTitle(),
                requestBody.getDescription(),
                requestBody.getCurrency());

        return new ResponseEntity(
                Collections.singletonMap("answer", "Account with title " + account.getTitle() + " successfuly created"),
                HttpStatus.OK);
    }
}