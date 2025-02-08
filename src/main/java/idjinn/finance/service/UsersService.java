package idjinn.finance.service;

import idjinn.finance.dto.users.CreateUserDTO;
import idjinn.finance.dto.users.LoginDTO;
import idjinn.finance.dto.users.UserDTO;
import idjinn.finance.model.User;
import idjinn.finance.repository.UsersRepository;
import idjinn.finance.util.errors.common.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordService passwordService;

    public Optional<UserDTO> findUserById(final @Valid UUID id) {
        return usersRepository.findUserById(id).map(UserDTO::fromUser);
    }

    public UserDTO createUser(final @Valid CreateUserDTO createUserDTO) {
        final var user = new User();
        user.setName(createUserDTO.getUserName());
        user.setEmail(createUserDTO.getEmail());

        final var hashedPassword = passwordService.hashPassword(createUserDTO.getPassword());
        user.setPasswordHash(hashedPassword.hash());

        usersRepository.save(user);
        return UserDTO.fromUser(user);
    }

    public UserDTO login(final @Valid LoginDTO loginDTO) {
        final var user = this.usersRepository.findUserByEmail(loginDTO.getEmail());
        if (user.isEmpty()) throw new NotFoundException("Invalid credentials");

        if (!this.passwordService.matchPassword(loginDTO.getPassword(), user.get().getPasswordHash()))
            throw new NotFoundException("Invalid credentials");

        return UserDTO.fromUser(user.get());
    }
}
