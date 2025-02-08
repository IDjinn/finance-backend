package idjinn.finance.dto.users;

import idjinn.finance.model.User;
import idjinn.finance.util.Strings;

import java.util.UUID;

public record UserResponseDTO(
        UUID Id,
        String Name,
        String Email
) {
    public static UserResponseDTO fromUser(final User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                Strings.maskEmail(user.getEmail())
        );
    }
}
