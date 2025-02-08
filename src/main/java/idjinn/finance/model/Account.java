package idjinn.finance.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "id"})
)
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotBlank
    @Schema(example = "Nubank")
    @Column(nullable = false)
    @Size(max = 70)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @NotBlank
    @Schema(example = "127540-X")
    @Column(nullable = false, updatable = false)
    @Size(max = 20)
    private String id;

    @NotBlank
    @Schema(example = "127540-X")
    @Column(nullable = false)
    @Size(max = 20)
    private String agency;


    @Column(precision = 10, scale = 2, nullable = false) // TODO: later support for multi-currency balance
    @Schema(example = "13.00")
    private BigDecimal balance;
}
