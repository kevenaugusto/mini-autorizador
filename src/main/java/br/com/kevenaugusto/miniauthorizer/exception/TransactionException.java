package br.com.kevenaugusto.miniauthorizer.exception;

import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import lombok.Getter;

@Getter
public class TransactionException extends RuntimeException {

    private final TransactionStatus status;

    public TransactionException(TransactionStatus status) {
        super();
        this.status = status;
    }

}
