package idjinn.finance.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    @Email
    @Schema(example = "jhon.doe@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    @Schema(example = "secretP@ssw0Rd!")
    private String password;
}
