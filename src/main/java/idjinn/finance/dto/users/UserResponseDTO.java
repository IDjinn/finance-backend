package idjinn.finance.dto.users;

import idjinn.finance.model.Account;
import idjinn.finance.model.CreditCard;
import idjinn.finance.model.User;
import idjinn.finance.util.Strings;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
        UUID Id,
        String Name,
        String Email,
        List<UUID> Accounts,
        List<UUID> CreditCards
) {
    public static UserResponseDTO fromUser(final User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                Strings.maskEmail(user.getEmail()),
                user.getAccounts().stream().map(Account::getUuid).toList(),
                user.getCreditCards().stream().map(CreditCard::getUuid).toList()
        );
    }
}
