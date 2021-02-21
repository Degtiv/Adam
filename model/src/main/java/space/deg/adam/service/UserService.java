package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.user.Credentials;
import space.deg.adam.domain.user.Role;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByCredentials_Username(username);
    }

    public void addUser(Credentials credentials, Role role) {
        User user = new User(credentials);
        user.setActive(true);
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
    }
}
