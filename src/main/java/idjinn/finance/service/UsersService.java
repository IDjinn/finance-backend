package idjinn.finance.service;

import idjinn.finance.dto.users.UserDTO;
import idjinn.finance.model.User;
import idjinn.finance.dto.users.CreateUserDTO;
import idjinn.finance.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordService passwordService;

    public Optional<UserDTO> findAccountById(UUID id) {
        return usersRepository.findUserById(id).map(UserDTO::fromUser);
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        final var user = new User();
        user.setName(createUserDTO.getUserName());

        final var hashedPassword = passwordService.hashPassword(createUserDTO.getPassword());
        user.setPasswordHash(hashedPassword.hash());

        usersRepository.save(user);
        return UserDTO.fromUser(user);
    }
}
