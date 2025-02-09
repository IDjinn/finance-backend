package idjinn.finance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(
        name = "credit_cards",
        uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "account_id", "uuid"})
)
@ToString(exclude = {"owner", "account"})
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Schema(example = "1234")
    @Column(nullable = false)
    @Size(min = 4, max = 4)
    private String lastDigits;

    @Schema(example = "black bird")
    @Column(nullable = false)
    @Size(min = 3, max = 15)
    private String nickName;

    @Schema(example = "01/01/2001")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expiryDate;
}
