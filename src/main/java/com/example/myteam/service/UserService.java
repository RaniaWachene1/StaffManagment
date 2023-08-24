import com.example.myteam.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.myteam.repository.UserRepository;


@AllArgsConstructor
@Service
@EnableWebSecurity
public class UserService implements UserDetailsService {
private final  static String USER_NOT_FOUND ="User Name does not exist";
private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username).
                orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND,username)));
    }
}
