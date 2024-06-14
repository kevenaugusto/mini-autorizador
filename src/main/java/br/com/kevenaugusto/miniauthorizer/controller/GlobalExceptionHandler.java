package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.exception.CardAlreadyExistsException;
import br.com.kevenaugusto.miniauthorizer.exception.CardNotFoundException;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import br.com.kevenaugusto.miniauthorizer.singleton.TransactionMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<CardResponseDto> handleCardAlreadyExistsException(CardAlreadyExistsException error) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new CardResponseDto(error.getPassword(), error.getCardNumber()));
    }

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleCardNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ExceptionHandler(TransactionException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<TransactionStatus> handleTransactionException(TransactionException error) {
        var transactionMap = TransactionMap.getInstance();
        transactionMap.unlockCard(error.getCardNumber());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorsMap.put(fieldName, errorMessage);
        });
        return errorsMap;
    }

}
