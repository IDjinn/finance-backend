package idjinn.finance.service;

import idjinn.finance.dto.auth.LoginDTO;
import idjinn.finance.dto.auth.LoginResponseDTO;
import idjinn.finance.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordService passwordService;
    private final UsersRepository usersRepository;

    public Optional<LoginResponseDTO> authenticate(final LoginDTO loginDTO) {
        final var email = loginDTO.getEmail();
        final var userOptional = usersRepository.findUserByEmail(email);
        if (userOptional.isEmpty())
            return Optional.empty();

        final var user = userOptional.get();
        final var password = loginDTO.getPassword();
        if (!passwordService.matchPassword(password, user.getPasswordHash()))
            return Optional.empty();

        return Optional.of(LoginResponseDTO.fromUser(user));
    }
}
