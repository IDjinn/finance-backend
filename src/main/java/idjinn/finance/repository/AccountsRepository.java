package idjinn.finance.repository;

import idjinn.finance.model.Account;
import idjinn.finance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountsRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountByUuid(final UUID uuid);

    List<Account> getAccountByOwner(final User owner);

    List<Account> getAccountByOwner_Id(final UUID owner);
}
