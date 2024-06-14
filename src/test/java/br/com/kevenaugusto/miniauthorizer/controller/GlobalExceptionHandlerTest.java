package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.exception.CardAlreadyExistsException;
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

}
