package idjinn.finance.dto.creditcard;

import com.fasterxml.jackson.annotation.JsonFormat;
import idjinn.finance.dto.users.UserResponseDTO;
import idjinn.finance.model.CreditCard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CreditCardResponseDTO {
    private UserResponseDTO owner;

    @Schema(example = "1234")
    private String lastDigits;

    @Schema(example = "black bird")
    private String nickName;

    @Schema(example = "01/01/2001")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expiryDate;

    public static CreditCardResponseDTO fromCreditCard(final CreditCard creditCard) {
        return new CreditCardResponseDTO();
    }
}
