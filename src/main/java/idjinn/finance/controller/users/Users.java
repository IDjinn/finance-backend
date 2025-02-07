package idjinn.finance.controller.users;

import idjinn.finance.dto.users.UserDTO;
import idjinn.finance.dto.users.CreateUserDTO;
import idjinn.finance.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class Users {

    private final UsersService usersService;

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getAccountByUUID(@PathVariable UUID id) {
        return usersService.findAccountById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping("/create")
    public UserDTO createAccount(@RequestBody @Valid final CreateUserDTO createUserDTO) {
        return usersService.createUser(createUserDTO);
    }
}
