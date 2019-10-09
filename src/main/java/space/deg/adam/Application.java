package space.deg.adam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import space.deg.adam.domain.Role;
import space.deg.adam.domain.User;
import space.deg.adam.repository.UserRepository;

import java.util.Collections;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner AdminCreation(UserRepository userRepository) {
        return r -> {
            User user = new User();
            user.setUsername("Admin");
            user.setPassword("Admin");
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(user);
        };
    }
}