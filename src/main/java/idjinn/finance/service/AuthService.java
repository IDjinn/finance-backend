package idjinn.finance.service;

import idjinn.finance.dto.auth.LoginDTO;
import idjinn.finance.dto.auth.LoginResponseDTO;
import idjinn.finance.repository.UsersRepository;
import idjinn.finance.security.JwtTokenService;
import idjinn.finance.security.UserDetailsImpl;
import idjinn.finance.util.errors.common.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordService passwordService;
    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public Optional<LoginResponseDTO> authenticate(final LoginDTO loginDTO) {
        try {
            final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            final var userDetails = (UserDetailsImpl) authentication.getPrincipal();

            final var userOptional = usersRepository.findUserByEmail(loginDTO.getEmail());
            if (userOptional.isEmpty())
                return Optional.empty();

            final var token = jwtTokenService.generateToken(userDetails);
            return Optional.of(LoginResponseDTO.fromUser(userOptional.get(), token));
        } catch (AuthenticationException authenticationException) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
