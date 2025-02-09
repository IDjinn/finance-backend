package idjinn.finance.dto.users;

import idjinn.finance.model.Account;
import idjinn.finance.model.CreditCard;
import idjinn.finance.model.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID Id;
    private String Name;
    private String Email;
    private List<UUID> Accounts;
    private List<UUID> CreditCards;

    public static UserResponseDTO fromUser(final User user) {
        final var dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAccounts(user.getAccounts().stream().map(Account::getUuid).toList());
        dto.setCreditCards(user.getCreditCards().stream().map(CreditCard::getUuid).toList());
        return dto;
    }
}
