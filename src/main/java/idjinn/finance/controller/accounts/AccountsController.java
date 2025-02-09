package idjinn.finance.controller.accounts;

import idjinn.finance.dto.accounts.AccountResponseDTO;
import idjinn.finance.dto.accounts.CreateAccountDTO;
import idjinn.finance.service.AccountsService;
import idjinn.finance.util.errors.common.NotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found", content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content),
    })
    @Cacheable("accounts")
    @GetMapping("/{id}")
    public AccountResponseDTO getAccountById(@PathVariable @Valid UUID id) {
        return accountsService.getAccountById(id).map(AccountResponseDTO::fromAccount).orElseThrow(() ->
                new NotFoundException("Account was not found.")
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created", content = @Content(schema = @Schema(implementation = AccountResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> create(final @Valid @RequestBody CreateAccountDTO createAccountDTO) {
        return new ResponseEntity<>(AccountResponseDTO.fromAccount(accountsService.createAccount(createAccountDTO)), HttpStatus.CREATED);
    }
}
