package idjinn.finance.security;

import idjinn.finance.service.PasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    private final PasswordService passwordService;

    public CustomPasswordEncoder(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordService.hashPassword(rawPassword.toString()).hash();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordService.matchPassword(rawPassword.toString(), encodedPassword);
    }
}
