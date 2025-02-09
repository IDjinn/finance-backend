package idjinn.finance.controller.creditcard;

import idjinn.finance.dto.creditcard.CreateCreditCardDTO;
import idjinn.finance.dto.creditcard.CreditCardResponseDTO;
import idjinn.finance.service.CreditCardsService;
import idjinn.finance.util.errors.common.NotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardsService creditCardsService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credit Card found", content = @Content(schema = @Schema(implementation = CreditCardResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Credit Card not found", content = @Content)
    })
    @Cacheable("creditCards")
    @GetMapping("/{id}")
    public CreditCardResponseDTO getCreditCard(@PathVariable("id") @Valid final UUID id) {
        return creditCardsService.getCreditCard(id).map(CreditCardResponseDTO::fromCreditCard).orElseThrow(() -> new NotFoundException("Credit Card not found"));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credit Card created", content = @Content(schema = @Schema(implementation = CreditCardResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Credit Card already exists", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<CreditCardResponseDTO> create(@RequestBody @Valid final CreateCreditCardDTO creditCardDTO) {
        return new ResponseEntity<>(creditCardsService.createCreditCard(creditCardDTO), HttpStatus.CREATED);
    }
}
