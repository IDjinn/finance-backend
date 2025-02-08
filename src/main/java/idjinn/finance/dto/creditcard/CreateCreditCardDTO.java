package idjinn.finance.dto.creditcard;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CreateCreditCardDTO {
    @Schema(example = "1234")
    @NotBlank
    @Size(min = 4, max = 4)
    private String lastDigits;

    @Schema(example = "black bird")
    @NotBlank
    @Size(min = 3, max = 16)
    private String nickName;

    @Schema(example = "01/01/2001")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expiryDate;

    @Schema(example = "1d9608bf-37ee-4788-981c-0745c827f0ed")
    private java.util.UUID accountId;

    @Schema(example = "1b69e0c6-15a5-4f60-963b-f128627140e4")
    private java.util.UUID ownerId;
}
