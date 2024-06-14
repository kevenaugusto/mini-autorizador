package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.exception.CardAlreadyExistsException;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void shouldReturnAnEntityWithStatusUnprocessable() {
        var cardResponse = new CardResponseDto("1234", "6549873025634501");
        var cardAlreadyExistsException = new CardAlreadyExistsException(cardResponse.senha(), cardResponse.numeroCartao());
        var responseEntity = globalExceptionHandler.handleCardAlreadyExistsException(cardAlreadyExistsException);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), cardResponse);
    }

    @Test
    void shouldReturnAnEntityWithStatusNotFoundAndNullBody() {
        var responseEntity = globalExceptionHandler.handleCardNotFoundException();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void shouldReturnAnEntityWithStatusUnprocessableAndBodyWithInsufficientBalance() {
        var responseEntity = globalExceptionHandler.handleTransactionException(new TransactionException(TransactionStatus.SALDO_INSUFICIENTE));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(TransactionStatus.SALDO_INSUFICIENTE, responseEntity.getBody());
    }

    @Test
    void shouldReturnAnEntityWithStatusUnprocessableAndBodyWithInvalidPassword() {
        var responseEntity = globalExceptionHandler.handleTransactionException(new TransactionException(TransactionStatus.SENHA_INVALIDA));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(TransactionStatus.SENHA_INVALIDA, responseEntity.getBody());
    }

    @Test
    void shouldReturnAnEntityWithStatusUnprocessableAndBodyWithInexistentCard() {
        var responseEntity = globalExceptionHandler.handleTransactionException(new TransactionException(TransactionStatus.CARTAO_INEXISTENTE));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(TransactionStatus.CARTAO_INEXISTENTE, responseEntity.getBody());
    }

}
