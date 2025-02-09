package idjinn.finance.dto.users;

import idjinn.finance.util.auth.RoleName;
import idjinn.finance.util.validators.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank
    @Size(min = 2, max = 12)
    @Schema(example = "Jhon Doe")
    private String userName;

    @NotBlank
    @Password
    @Schema(example = "secretP4ssw0rd!")
    private String password;

    @NotBlank
    @Email
    @Schema(example = "jhon.doe@gmail.com")
    private String email;

    private RoleName role;
}
