package br.com.kevenaugusto.miniauthorizer.service;

import br.com.kevenaugusto.miniauthorizer.builder.CardBuilder;
import br.com.kevenaugusto.miniauthorizer.builder.TransactionBuilder;
import br.com.kevenaugusto.miniauthorizer.dto.TransactionDto;
import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import br.com.kevenaugusto.miniauthorizer.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private CardRepository cardRepository;

    @Test
    void shouldReturnTransactionStatusOkWhenTransactionIsSuccessful() {
        var existentCard = CardBuilder.buildExistentCard();
        when(cardRepository.findByNumeroCartao(existentCard.getNumeroCartao())).thenReturn(Optional.of(existentCard));
        var transactionDto = new TransactionDto(existentCard.getNumeroCartao(), existentCard.getSenha(), BigDecimal.valueOf(10.00));
        assertEquals(TransactionStatus.OK, transactionService.doTransaction(transactionDto));
    }

    @Test
    void shouldThrowTransactionExceptionWhenCardNotFound() {
        when(cardRepository.findByNumeroCartao(any())).thenReturn(Optional.empty());
        assertThrows(TransactionException.class, () -> transactionService.doTransaction(TransactionBuilder.buildValidTransaction()));
    }

    @Test
    void shouldThrowTransactionExceptionWhenPasswordIsWrong() {
        var existentCard = CardBuilder.buildExistentCard();
        when(cardRepository.findByNumeroCartao(existentCard.getNumeroCartao())).thenReturn(Optional.of(existentCard));
        var transactionDto = new TransactionDto(existentCard.getNumeroCartao(), "0000", BigDecimal.ZERO);
        assertThrows(TransactionException.class, () -> transactionService.doTransaction(transactionDto));
    }

    @Test
    void shouldThrowTransactionExceptionWhenBalanceIsInsufficient() {
        var existentCard = CardBuilder.buildExistentCard();
        existentCard.setSaldo(BigDecimal.valueOf(5.00));
        when(cardRepository.findByNumeroCartao(existentCard.getNumeroCartao())).thenReturn(Optional.of(existentCard));
        assertThrows(TransactionException.class, () -> transactionService.doTransaction(TransactionBuilder.buildValidTransaction()));
    }

}
