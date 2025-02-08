package idjinn.finance.service;

import idjinn.finance.dto.creditcard.CreateCreditCardDTO;
import idjinn.finance.dto.creditcard.CreditCardResponseDTO;
import idjinn.finance.model.CreditCard;
import idjinn.finance.repository.AccountsRepository;
import idjinn.finance.repository.CreditCardsRepository;
import idjinn.finance.repository.UsersRepository;
import idjinn.finance.util.errors.common.CollisionException;
import idjinn.finance.util.errors.common.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardsService {
    private final CreditCardsRepository creditCardsRepository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;

    public Optional<CreditCard> getCreditCard(final @Valid UUID uuid) {
        return creditCardsRepository.findById(uuid);
    }

    public Optional<CreditCard> getCreditCardsByOwnerId(final @Valid UUID ownerUuid) {
        return creditCardsRepository.findCreditCardByOwner_Id(ownerUuid);
    }

    public CreditCardResponseDTO createCreditCard(final @Valid CreateCreditCardDTO creditCardDTO) {
        final var collision = creditCardsRepository.findCreditCardByAccountUuidAndOwnerId(creditCardDTO.getAccountId(), creditCardDTO.getOwnerId());
        if (collision.isPresent() && collision.get().getLastDigits().equals(creditCardDTO.getLastDigits()))
            throw new CollisionException("Credit Card already exists");

        final var userOptional = usersRepository.findUserById(creditCardDTO.getOwnerId());
        if (userOptional.isEmpty()) throw new NotFoundException("User not found");

        final var accountOptional = accountsRepository.findAccountByUuid(creditCardDTO.getAccountId());
        if (accountOptional.isEmpty()) throw new NotFoundException("Account not found");

        final var account = accountOptional.get();
        final var user = userOptional.get();

        final var creditCard = new CreditCard();
        creditCard.setUuid(UUID.randomUUID());
        creditCard.setOwner(user);
        creditCard.setAccount(account);
        creditCard.setNickName(creditCardDTO.getNickName());
        creditCard.setLastDigits(creditCardDTO.getLastDigits());
        creditCardsRepository.save(creditCard);

        return CreditCardResponseDTO.fromCreditCard(creditCard);
    }
}
