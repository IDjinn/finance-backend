package idjinn.finance.dto.users;

import idjinn.finance.model.User;
import idjinn.finance.util.Strings;

import java.util.UUID;

public record UserDTO(
        UUID Id,
        String Name,
        String Email
) {
    public static UserDTO fromUser(final User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                Strings.maskEmail(user.getEmail())
        );
    }
}
