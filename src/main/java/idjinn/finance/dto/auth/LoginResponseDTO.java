package idjinn.finance.dto.auth;

import idjinn.finance.model.Role;
import idjinn.finance.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LoginResponseDTO {
    private UUID id;

    @Schema(example = "Jhon Doe")
    private String name;

    @Schema(example = "jhon.doe@gmail.com")
    private String email;

    private String token;

    private List<Role> roles;

    public static LoginResponseDTO fromUser(final User user, final String token) {
        final var dto = new LoginResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setToken(token);
        dto.setRoles(user.getRoles());
        return dto;
    }
}
