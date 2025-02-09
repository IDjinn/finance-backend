package idjinn.finance.dto.users;

import idjinn.finance.util.auth.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank
    @Size(min = 2, max = 12)
    @Schema(name = "Jhon Doe")
    private String userName;

    @NotBlank
    @Size(min = 8, max = 20)
    @Schema(example = "secretP4ssw0rd!")
    private String password;

    @NotBlank
    @Email
    @Schema(example = "jhon.doe@gmail.com")
    private String email;

    @NotBlank
    private RoleName role;
}
