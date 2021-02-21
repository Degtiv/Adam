package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.token.Token;
import space.deg.adam.domain.user.Role;
import space.deg.adam.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public String getHash(Token token) {
        return new SCryptPasswordEncoder().encode(token.toString());
    }

    public boolean checkTokenHashForUserAndRole(String tokenHash, String username, Role role) {
        Token token = getTokenByUsernameAndRole(username, role);
        if (token == null)
            return false;
        return matchTokens(token, tokenHash);
    }

    public boolean matchTokens(Token token, String tokenHash) {
        return new SCryptPasswordEncoder().matches(token.toString(), tokenHash);
    }

    public Token getTokenByUsernameAndRole(String username, Role role) {
        return tokenRepository.findByUsernameAndRole(username, role);
    }

    public List<Token> getAllTokens() {
        return (List<Token>) tokenRepository.findAll();
    }

    public Token generateToken(String username, Role role) {
        Token token = getTokenByUsernameAndRole(username, role);

        if (token == null) {
            token = new Token();
            token.setUsername(username);
            token.setRole(role);
        }

        token.setStartDate(LocalDateTime.now());
        tokenRepository.save(token);

        return token;
    }
}
