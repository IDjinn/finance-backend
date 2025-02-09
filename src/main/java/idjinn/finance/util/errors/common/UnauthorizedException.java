package idjinn.finance.util.errors.common;

import idjinn.finance.util.errors.FinanceException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends FinanceException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
