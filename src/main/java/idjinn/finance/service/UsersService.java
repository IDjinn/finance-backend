package idjinn.finance.service;

import idjinn.finance.dto.users.CreateUserDTO;
import idjinn.finance.dto.users.UserResponseDTO;
import idjinn.finance.model.User;
import idjinn.finance.repository.UsersRepository;
import idjinn.finance.util.errors.FinanceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordService passwordService;

    public Optional<UserResponseDTO> findUserById(final @Valid UUID id) {
        return usersRepository.findUserById(id).map(UserResponseDTO::fromUser);
    }

    public UserResponseDTO createUser(final @Valid CreateUserDTO createUserDTO) {
        final var existentUser = usersRepository.findUserByEmail(createUserDTO.getEmail());
        if (existentUser.isPresent())
            throw new FinanceException(HttpStatus.CONFLICT, "Email already exists");

        final var user = new User();
        user.setName(createUserDTO.getUserName());
        user.setEmail(createUserDTO.getEmail());

        final var hashedPassword = passwordService.hashPassword(createUserDTO.getPassword());
        user.setPasswordHash(hashedPassword.hash());

        usersRepository.save(user);
        return UserResponseDTO.fromUser(user);
    }
}
