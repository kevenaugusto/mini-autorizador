package br.com.kevenaugusto.miniauthorizer.exception;

import lombok.Getter;

@Getter
public class CardAlreadyExists extends RuntimeException {

    private final String password;
    private final String cardNumber;

    public CardAlreadyExists(String password, String cardNumber) {
        super();
        this.password = password;
        this.cardNumber = cardNumber;
    }

}
