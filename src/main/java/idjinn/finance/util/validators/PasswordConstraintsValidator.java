package idjinn.finance.util.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    @Value("${security.passwords.min}")
    private int min;

    @Value("${security.passwords.max}")
    private int max;

    @Value("${security.passwords.include_upper_case}")
    private boolean upperCase;

    @Value("${security.passwords.include_lower_case}")
    private boolean lowerCase;

    @Value("${security.passwords.include_digit}")
    private boolean digit;

    @Value("${security.passwords.include_symbol}")
    private boolean symbol;

    @Override
    public boolean isValid(final String password, ConstraintValidatorContext context) {
        if (password == null) {
            context.buildConstraintViolationWithTemplate("cannot be null.")
                    .addConstraintViolation();
            return false;
        }

        final var errorMessages = new ArrayList<String>();
        if (password.length() < min || password.length() > max) {
            errorMessages.add("must be between '" + min + "' and '" + max + "' characters.");
        }

        if (upperCase && !password.matches(".*[A-Z].*")) {
            errorMessages.add("must contain at least one uppercase letter.");
        }

        if (lowerCase && !password.matches(".*[a-z].*")) {
            errorMessages.add("must contain at least one lowercase letter.");
        }

        if (digit && !password.matches(".*\\d.*")) {
            errorMessages.add("must contain at least one digit.");
        }

        if (symbol && !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            errorMessages.add("must contain at least one special character.");
        }

        if (errorMessages.isEmpty())
            return true;

        for (final var message : errorMessages) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return false;
    }
}
