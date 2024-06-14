package br.com.kevenaugusto.miniauthorizer.builder;

import br.com.kevenaugusto.miniauthorizer.dto.InputCardDto;
import br.com.kevenaugusto.miniauthorizer.dto.TransactionDto;

import java.math.BigDecimal;

public class TransactionBuilder {

    public static TransactionDto buildValidTransaction() {
        return new TransactionDto("6549873025634501", "1234", BigDecimal.valueOf(10.00));
    }

    public static TransactionDto buildTransactionWithInvalidCardNumber() {
        return new TransactionDto(null, "5678", BigDecimal.ZERO);
    }

    public static TransactionDto buildTransactionWithAlphanumericCardNumber() {
        return new TransactionDto("65ab87df25qw45xy", "9012", BigDecimal.ZERO);
    }

    public static TransactionDto buildTransactionWithCardNumberLessThanMinimum() {
        return new TransactionDto("654987302563450", "3456", BigDecimal.ZERO);
    }

    public static TransactionDto buildTransactionWithCardNumberGreaterThanMaximum() {
        return new TransactionDto("65498730256345012", "7890", BigDecimal.ZERO);
    }

    public static TransactionDto buildTransactionWithInvalidPassword() {
        return new TransactionDto("6549873025634502", null, BigDecimal.ZERO);
    }

    public static TransactionDto buildTransactionWithInvalidValue() {
        return new TransactionDto("6549873025634503", "1234", null);
    }

    public static TransactionDto buildTransactionWithInsufficientBalance() {
        return new TransactionDto("6549873025634504", "1234", BigDecimal.valueOf(1000.00));
    }

    public static TransactionDto buildTransactionWithWrongPassword() {
        return new TransactionDto("6549873025634505", "0000", BigDecimal.valueOf(10.00));
    }

    public static TransactionDto buildTransactionWithInexistentCardNumber() {
        return new TransactionDto("0000000000000000", "1234", BigDecimal.valueOf(10.00));
    }

}
