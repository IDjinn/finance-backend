package idjinn.finance.dto.auth;

import idjinn.finance.model.User;
import idjinn.finance.util.Strings;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record LoginResponseDTO(
        UUID Id,
        @Schema(example = "Jhon Doe")
        String Name,
        @Schema(example = "jhon.doe@gmail.com")
        String Email,
        String Token
) {
    public static LoginResponseDTO fromUser(final User user) {
        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                Strings.maskEmail(user.getEmail()),
                null
        );
    }
}
