package space.deg.adam.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class Credentials {
    private String username;
    private String password;
    private String email;
}
