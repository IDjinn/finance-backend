package idjinn.finance.dto.auth;

import idjinn.finance.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponseDTO {
    private UUID Id;

    @Schema(example = "Jhon Doe")
    private String Name;

    @Schema(example = "jhon.doe@gmail.com")
    private String Email;

    private String Token;

    public static LoginResponseDTO fromUser(final User user) {
        final var dto = new LoginResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setToken("");
        return dto;
    }
}
