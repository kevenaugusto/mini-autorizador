package br.com.kevenaugusto.miniauthorizer.exception;

import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import lombok.Getter;

@Getter
public class TransactionException extends RuntimeException {

    private final TransactionStatus status;
    private final String cardNumber;

    public TransactionException(TransactionStatus status, String cardNumber) {
        super();
        this.status = status;
        this.cardNumber = cardNumber;
    }

}
