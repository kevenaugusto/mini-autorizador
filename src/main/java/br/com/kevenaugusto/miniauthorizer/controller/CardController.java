package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.dto.InputCardDto;
import br.com.kevenaugusto.miniauthorizer.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Cartões")
public class CardController {

    @Autowired
    CardService cardService;

    @Operation(summary = "Efetua a criação de um novo cartão", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
        @ApiResponse(responseCode = "422", description = "Já existe um cartão com esse número"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "400", description = "Erro na validação dos parâmetros")
    })
    @PostMapping(value = "/cartoes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardResponseDto> createCard(@RequestBody @Valid InputCardDto inputCardDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(inputCardDto));
    }

    @Operation(summary = "Recupera o saldo de um cartão", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo recuperado com sucesso"),
            @ApiResponse(responseCode = "404", description = "O cartão informado não existe"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação")
    })
    @GetMapping("/cartoes/{cardNumber}")
    public ResponseEntity<BigDecimal> getCardBalance(@PathVariable(value = "cardNumber") String cardNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardBalance(cardNumber));
    }

}
