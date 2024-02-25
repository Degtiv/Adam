package space.deg.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.GoalRepository;
import space.deg.adam.repository.MilestoneRepository;
import space.deg.adam.repository.TransactionRepository;
import space.deg.adam.repository.UserRepository;
import space.deg.adam.utils.encryption.AESUtils;
import space.deg.adam.utils.encryption.EncryptionPrefixes;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private GoalRepository goalRepository;

  @Autowired
  private MilestoneRepository milestoneRepository;

  @Autowired
  private UserRepository userRepository;

  public void deleteUser(User user) {
    transactionRepository.deleteAll(transactionRepository.findByUser(user));
    goalRepository.deleteAll(goalRepository.findByUser(user));
    milestoneRepository.deleteAll(milestoneRepository.findByUser(user));

    userRepository.delete(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }

  public String generateAccessToken(String username) throws UsernameNotFoundException {
    return getAccessTokenByUsername(username);
  }

  public User loadUserByAccessToken(String accessToken) throws UsernameNotFoundException {
    return userRepository.findAll()
        .stream()
        .filter(user ->
            accessToken.equals(getAccessTokenByUsername(user.getUsername())))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Пользователь не найден"));
  }


  public String getAccessTokenByUsername(String username) {
    return EncryptionPrefixes.TG_API_KEY_PREFIX + AESUtils.encrypt(username, username) + EncryptionPrefixes.TG_API_KEY_SUFFIX;
  }
}
