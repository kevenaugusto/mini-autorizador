package br.com.kevenaugusto.miniauthorizer.service;

import br.com.kevenaugusto.miniauthorizer.builder.CardBuilder;
import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.exception.CardAlreadyExistsException;
import br.com.kevenaugusto.miniauthorizer.exception.CardNotFoundException;
import br.com.kevenaugusto.miniauthorizer.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    private static final String INVALID_CARD_NUMBER = "0000000000000000";

    @Test
    void shouldThrowCardAlreadyExistsExceptionWhenCardExsists() {
        when(cardRepository.findByNumeroCartao(any())).thenReturn(Optional.of(CardBuilder.buildExistentCard()));
        assertThrows(CardAlreadyExistsException.class, () -> cardService.createCard(CardBuilder.buildValidCard()));
    }

    @Test
    void shouldReturnValidCardResponse() {
        var validCard = CardBuilder.buildValidCard();
        assertEquals(new CardResponseDto(validCard.senha(), validCard.numeroCartao()), cardService.createCard(validCard));
    }

    @Test
    void shouldThrowCardNotFoundWhenCardIsInvalid() {
        when(cardRepository.findByNumeroCartao(any())).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.getCardBalance(INVALID_CARD_NUMBER));
    }

    @Test
    void shouldReturnBalanceValueWhenCardExists() {
        var existentCard = CardBuilder.buildExistentCard();
        when(cardRepository.findByNumeroCartao(existentCard.getNumeroCartao())).thenReturn(Optional.of(existentCard));
        assertEquals(existentCard.getSaldo(), cardService.getCardBalance(existentCard.getNumeroCartao()));
    }

}
