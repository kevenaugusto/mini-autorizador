package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import br.com.kevenaugusto.miniauthorizer.singleton.TransactionMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class TransactionExceptionTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private static final String CARD_NUMBER_EXAMPLE = "6549873025634501";

    @BeforeEach
    void init() {
        var transactionMap = TransactionMap.getInstance();
        transactionMap.lockCard(CARD_NUMBER_EXAMPLE);
    }

    @AfterEach
    void teardown() {
        var transactionMap = TransactionMap.getInstance();
        assertNull(transactionMap.unlockCard(CARD_NUMBER_EXAMPLE));
    }

    @Test
    void shouldReturnAnEntityWithStatusUnprocessableAndBodyWithInsufficientBalance() {
        var responseEntity = globalExceptionHandler.handleTransactionException(new TransactionException(TransactionStatus.SALDO_INSUFICIENTE, CARD_NUMBER_EXAMPLE));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(TransactionStatus.SALDO_INSUFICIENTE, responseEntity.getBody());
    }

    @Test
    void shouldReturnAnEntityWithStatusUnprocessableAndBodyWithInvalidPassword() {
        var responseEntity = globalExceptionHandler.handleTransactionException(new TransactionException(TransactionStatus.SENHA_INVALIDA, CARD_NUMBER_EXAMPLE));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(TransactionStatus.SENHA_INVALIDA, responseEntity.getBody());
    }

    @Test
    void shouldReturnAnEntityWithStatusUnprocessableAndBodyWithInexistentCard() {
        var responseEntity = globalExceptionHandler.handleTransactionException(new TransactionException(TransactionStatus.CARTAO_INEXISTENTE, CARD_NUMBER_EXAMPLE));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(TransactionStatus.CARTAO_INEXISTENTE, responseEntity.getBody());
    }

}
