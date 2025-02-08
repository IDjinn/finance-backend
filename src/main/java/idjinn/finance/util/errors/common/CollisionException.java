package idjinn.finance.util.errors.common;

import idjinn.finance.util.errors.FinanceException;
import org.springframework.http.HttpStatus;

public class CollisionException extends FinanceException {
    public CollisionException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
