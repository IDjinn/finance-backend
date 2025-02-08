package idjinn.finance.controller.users;

import idjinn.finance.dto.users.CreateUserDTO;
import idjinn.finance.dto.users.LoginDTO;
import idjinn.finance.dto.users.UserDTO;
import idjinn.finance.service.UsersService;
import idjinn.finance.util.errors.FinanceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class Users {

    private final UsersService usersService;

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getAccountByUUID(@PathVariable UUID id) {
       return usersService.findUserById(id).orElseThrow(() -> new FinanceException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping("/create")
    public UserDTO createAccount(@RequestBody @Valid final CreateUserDTO createUserDTO) {
        return usersService.createUser(createUserDTO);
    }

    @GetMapping("/login")
    public UserDTO login(@RequestBody @Valid final LoginDTO loginDTO) {
        return usersService.login(loginDTO);
    }
}
