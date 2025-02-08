package idjinn.finance.dto.auth;

import idjinn.finance.model.User;
import idjinn.finance.util.Strings;

import java.util.UUID;

public record LoginResponseDTO(
        UUID Id,
        String Name,
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
