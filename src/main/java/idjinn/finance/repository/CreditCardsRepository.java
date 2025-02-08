package idjinn.finance.repository;

import idjinn.finance.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardsRepository extends JpaRepository<CreditCard, UUID> {
    Optional<CreditCard> findCreditCardByUuid(final UUID uuid);

    Optional<CreditCard> findCreditCardByOwner(final CreditCard owner);

    Optional<CreditCard> findCreditCardByOwner_Id(final UUID owner);

    Optional<CreditCard> findCreditCardByAccountUuidAndOwnerId(final UUID accountUuid, final UUID ownerId);
}
