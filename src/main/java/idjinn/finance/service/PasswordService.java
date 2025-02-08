package idjinn.finance.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class PasswordService {
    @Value("${passwords.pepper}")
    private String PEPPER;

    @Value("${passwords.cost}")
    private int COST;

    public HashedPassword hashPassword(final String password) {
        final var peppered = PEPPER + password;
        final var salted = new String(BCrypt.withDefaults().hash(COST, peppered.toCharArray()), StandardCharsets.UTF_8);
        return new HashedPassword(salted);
    }

    public boolean matchPassword(final String password, final String hashedPassword) {
        final var peppered = PEPPER + password;
        final var result = BCrypt.verifyer().verify(peppered.toCharArray(), hashedPassword.toCharArray());
        if (result.validFormat && result.verified) return true;

        log.error("Invalid hashed password: {}\n\n{}", hashedPassword, result);
        return false;
    }

    public record HashedPassword(String hash) {
    }
}
