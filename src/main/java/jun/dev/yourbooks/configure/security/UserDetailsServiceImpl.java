package jun.dev.yourbooks.configure.security;

import jun.dev.yourbooks.exception.NotFoundException;
import jun.dev.yourbooks.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByEmail(username).orElseThrow(()->
                new NotFoundException("No such a user found with email " + username));
    }
}
