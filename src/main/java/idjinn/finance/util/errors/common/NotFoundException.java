package idjinn.finance.util.errors.common;

import idjinn.finance.util.errors.FinanceException;
import org.springframework.http.HttpStatusCode;

public class NotFoundException extends FinanceException {
    public NotFoundException(String message) {
        super(HttpStatusCode.valueOf(404), message);
    }
}
