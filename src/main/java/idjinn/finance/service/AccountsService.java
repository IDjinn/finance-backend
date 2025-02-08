package idjinn.finance.service;

import idjinn.finance.dto.accounts.CreateAccountDTO;
import idjinn.finance.model.Account;
import idjinn.finance.repository.AccountsRepository;
import idjinn.finance.repository.UsersRepository;
import idjinn.finance.util.errors.common.CollisionException;
import idjinn.finance.util.errors.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final UsersRepository usersRepository;

    public Optional<Account> getAccountById(final UUID uuid) {
        return accountsRepository.findById(uuid);
    }

    public Account createAccount(final CreateAccountDTO createAccountDTO) {
        final var userOptional = usersRepository.findUserById(UUID.fromString(createAccountDTO.getOwnerId()));
        if (userOptional.isEmpty()) throw new NotFoundException("User not found");

        final var user = userOptional.get();
        final var accounts = accountsRepository.getAccountByOwner(user);
        if (!accounts.isEmpty() && accounts.stream().anyMatch(account -> account.getId().equals(createAccountDTO.getId())))
            throw new CollisionException("Account already exists");

        final var account = new Account();
        account.setId(createAccountDTO.getId());
        account.setName(createAccountDTO.getName());
        account.setAgency(createAccountDTO.getAgency());
        account.setBalance(createAccountDTO.getBalance());
        account.setOwner(user);

        accountsRepository.save(account);
        return account;
    }
}
