package idjinn.finance.dto.accounts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDTO {
    @NotBlank
    @Schema(example = "Nubank")
    @Column(nullable = false)
    @Size(max = 70)
    private String name;

    @org.hibernate.validator.constraints.UUID
    @Schema(example = "1b69e0c6-15a5-4f60-963b-f128627140e4")
    private String ownerId;

    @NotBlank
    @Schema(example = "127540-X")
    @Size(max = 20)
    private String id;

    @NotBlank
    @Schema(example = "127540-X")
    @Size(max = 20)
    private String agency;


    @Schema(example = "13.00")
    private BigDecimal balance;
}
