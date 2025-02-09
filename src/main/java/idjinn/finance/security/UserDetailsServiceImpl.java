package idjinn.finance.security;

import idjinn.finance.repository.UsersRepository;
import idjinn.finance.util.errors.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userRepository.findUserByEmail(username).orElseThrow(() -> new NotFoundException("User not found."));
        return new UserDetailsImpl(user);
    }
}
