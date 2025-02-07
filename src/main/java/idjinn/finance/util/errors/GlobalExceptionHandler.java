package idjinn.finance.util.errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        final var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> MessageFormat.format("{0}: {1}", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        final var errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrors(errors);
        errorResponse.setMessage("Validation failed");
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FinanceException.class)
    public ResponseEntity<ErrorResponse> handleFinanceException(final FinanceException ex){
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatusCode.valueOf(ex.getErrorResponse().getStatus()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException ex){
        log.error(ex.getMessage(), ex);

        final var internalServerError = new ErrorResponse();
        internalServerError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        internalServerError.setMessage("Internal Server Error");
        internalServerError.setTimestamp(LocalDateTime.now());
        internalServerError.setErrors(Collections.emptyList());
        return new ResponseEntity<>(internalServerError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
