package idjinn.finance.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank
    @Size(min = 2, max = 12)
    private String userName;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}
