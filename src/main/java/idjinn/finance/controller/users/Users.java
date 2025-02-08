package idjinn.finance.controller.users;

import idjinn.finance.dto.users.CreateUserDTO;
import idjinn.finance.dto.users.UserDTO;
import idjinn.finance.model.User;
import idjinn.finance.service.UsersService;
import idjinn.finance.util.errors.FinanceException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class Users {

    private final UsersService usersService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    })
    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getAccountByUUID(@PathVariable UUID id) {
       return usersService.findUserById(id).orElseThrow(() -> new FinanceException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = User.class))),
    })
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createAccount(@RequestBody @Valid final CreateUserDTO createUserDTO) {
        return new ResponseEntity<>(usersService.createUser(createUserDTO), HttpStatus.CREATED);
    }
}
