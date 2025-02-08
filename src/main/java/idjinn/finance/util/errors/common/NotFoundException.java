package idjinn.finance.util.errors.common;

import idjinn.finance.util.errors.FinanceException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends FinanceException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
