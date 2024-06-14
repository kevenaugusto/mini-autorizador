package br.com.kevenaugusto.miniauthorizer.exception;

import lombok.Getter;

@Getter
public class CardAlreadyExistsException extends RuntimeException {

    private final String password;
    private final String cardNumber;

    public CardAlreadyExistsException(String password, String cardNumber) {
        super();
        this.password = password;
        this.cardNumber = cardNumber;
    }

}
