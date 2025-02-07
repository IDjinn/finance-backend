package idjinn.finance.util.errors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public class FinanceException extends RuntimeException {
    @Getter
    @Setter
    private ErrorResponse errorResponse;

    public FinanceException(final HttpStatusCode statusCode, final String message) {
        super(message);

        this.errorResponse = new ErrorResponse();
        this.errorResponse.setStatus(statusCode.value());
        this.errorResponse.setMessage(message);
        this.errorResponse.setTimestamp(LocalDateTime.now());
    }
}
