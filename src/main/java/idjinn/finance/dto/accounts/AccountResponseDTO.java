package idjinn.finance.dto.accounts;

import idjinn.finance.dto.users.UserResponseDTO;
import idjinn.finance.model.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountResponseDTO {
    @Id
    private UUID uuid;

    @NotBlank
    @Schema(example = "Nubank")
    private String name;

    @NotNull
    private UserResponseDTO owner;

    @NotBlank
    @Schema(example = "127540-X")
    private String id;

    @NotBlank
    @Schema(example = "127540-X")
    private String agency;


    @Schema(example = "13.00")
    private BigDecimal balance;

    public static AccountResponseDTO fromAccount(final Account account) {
        final var dto = new AccountResponseDTO();
        dto.setUuid(account.getUuid());
        dto.setName(account.getName());
        dto.setOwner(UserResponseDTO.fromUser(account.getOwner()));
        dto.setId(account.getId());
        dto.setAgency(account.getAgency());
        dto.setBalance(account.getBalance());

        return dto;
    }
}
