package idjinn.finance.dto.users;

import idjinn.finance.model.User;

import java.util.UUID;

public record UserDTO(
        UUID Id,
        String Name
) {
    public static UserDTO fromUser(final User user) {
        return new UserDTO(
                user.getId(),
                user.getName()
        );
    }
}
