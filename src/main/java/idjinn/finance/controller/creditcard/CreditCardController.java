package idjinn.finance.controller.creditcard;

import idjinn.finance.dto.creditcard.CreateCreditCardDTO;
import idjinn.finance.dto.creditcard.CreditCardResponseDTO;
import idjinn.finance.service.CreditCardsService;
import idjinn.finance.util.errors.common.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardsService creditCardsService;

    @GetMapping("/{id}")
    public CreditCardResponseDTO getCreditCard(@PathVariable("id") @Valid final UUID id) {
        return creditCardsService.getCreditCard(id).map(CreditCardResponseDTO::fromCreditCard).orElseThrow(() -> new NotFoundException("Credit Card not found"));
    }

    @PostMapping("/create")
    public ResponseEntity<CreditCardResponseDTO> create(@RequestBody @Valid final CreateCreditCardDTO creditCardDTO) {
        return new ResponseEntity<>(creditCardsService.createCreditCard(creditCardDTO), HttpStatus.CREATED);
    }
}
