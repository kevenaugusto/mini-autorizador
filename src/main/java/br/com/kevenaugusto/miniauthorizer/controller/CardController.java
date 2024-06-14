package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.dto.InputCardDto;
import br.com.kevenaugusto.miniauthorizer.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/cartoes")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody @Valid InputCardDto inputCardDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(inputCardDto));
    }

    @GetMapping("/cartoes/{cardNumber}")
    public ResponseEntity<BigDecimal> getCardBalance(@PathVariable(value = "cardNumber") String cardNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardBalance(cardNumber));
    }

}
