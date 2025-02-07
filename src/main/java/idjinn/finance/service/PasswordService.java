package idjinn.finance.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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

    public record HashedPassword(String hash) {
    }
}
