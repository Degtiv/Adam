package space.deg.adam.controller.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.deg.adam.domain.token.Token;
import space.deg.adam.domain.user.Credentials;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.service.TokenService;
import space.deg.adam.service.UserService;
import space.deg.adam.controller.api.utils.Error;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@RequestBody Credentials credentials) {
        User userFromRepository = (User) userService.loadUserByUsername(credentials.getUsername());
        if (userFromRepository != null) {
            Error error = new Error();
            error.setMessage(String.format("User with username %s already exists", userFromRepository.getCredentials().getUsername()));
            error.setSolution("Please, use another username");
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> map = new HashMap<>();
        userService.addUser(credentials, Role.USER);
        map.put("answer", String.format("User has been created"));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping(path = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity token(@RequestParam String username, @RequestParam String password, @RequestParam(name = "role") String roleTitle) {
        User userFromRepository = (User) userService.loadUserByUsername(username);
        Role role = Role.valueOf(roleTitle.toUpperCase());
        if (userFromRepository == null || userFromRepository.getPassword().compareTo(password) != 0) {
            Error error = new Error();
            error.setMessage("Password or username is incorrect");
            error.setSolution("Please, try another pair of username and password");

            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        if (!userFromRepository.getRoles().contains(role)) {
            Error error = new Error();
            error.setMessage("User has insufficient rights to get token with role " + roleTitle);

            return new ResponseEntity(error, HttpStatus.FORBIDDEN);
        }

        try {
            HashMap<String, Object> responseBody = new HashMap<>();
            Token token = tokenService.generateToken(userFromRepository.getUsername(), role);
            responseBody.put("token", tokenService.getHash(token));
            responseBody.put("username", token.getUsername());
            responseBody.put("startDateTime",  token.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            responseBody.put("endDateTime", token.getExpirationDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            responseBody.put("role", token.getRole().getAuthority().toLowerCase());

            return new ResponseEntity(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            Error error = new Error();
            error.setMessage("Could not generate token for username '" + username + "' and password '" + password + "'");

            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}