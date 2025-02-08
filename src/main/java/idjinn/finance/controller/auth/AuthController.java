package idjinn.finance.controller.auth;

import idjinn.finance.dto.auth.LoginDTO;
import idjinn.finance.dto.auth.LoginResponseDTO;
import idjinn.finance.service.AuthService;
import idjinn.finance.util.errors.FinanceException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Credentials", content = @Content),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid final LoginDTO loginDTO) {
        return authService.authenticate(loginDTO).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseThrow(() -> new FinanceException(
                HttpStatus.BAD_REQUEST,
                "Invalid Credentials"
        ));
    }
}
