package space.deg.adam.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import space.deg.adam.domain.user.events.FirstEnterUserEvent;
import space.deg.adam.domain.user.events.UserEvent;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserEvent> events = new HashSet<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    public void addUserEvent(UserEvent userEvent) {
        events.add(userEvent);
    }

    public Set<UserEvent> getActiveEvents() {
        return events.stream().filter(t -> t.isActive).collect(Collectors.toSet());
    }

    public Set<String> getActiveEventsTitles() {
        return getActiveEvents().stream().map(UserEvent::toString).collect(Collectors.toSet());
    }

    public boolean isAdmin() {
        return getAuthorities().contains(Role.ADMIN);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public boolean is(User user) {
        return this.id.equals(user.id);
    }
}
