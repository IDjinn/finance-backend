package idjinn.finance.dto.auth;

import idjinn.finance.util.validators.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    @Email
    @Schema(example = "jhon.doe@gmail.com")
    private String email;

    @NotBlank
    @Password
    @Schema(example = "secretP@ssw0Rd!")
    private String password;
}
